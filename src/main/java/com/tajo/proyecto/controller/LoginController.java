package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Cliente;
import com.tajo.proyecto.model.Usuario;
import com.tajo.proyecto.repository.ClienteRepository;
import com.tajo.proyecto.repository.UsuarioRepository;
import com.tajo.proyecto.repository.DashboardRepository; // <-- IMPORT NUEVO
import com.tajo.proyecto.repository.MaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private DashboardRepository dashboardRepo; // <-- INYECCIÓN DE LA MAGIA DE ORACLE

    @Autowired
    private MaterialRepository materialRepo;

    

    @GetMapping("/")
    public String index() {
        return "login";
    }

    // --- PROCESO DE LOGIN ---
    @PostMapping("/login")
    public String login(@RequestParam String identificacion,
            @RequestParam String contrasena,
            HttpSession session) {
        try {
            // Limpiamos espacios por si el usuario tecleó un espacio extra al final
            String idLimpio = identificacion.trim();

            // ==========================================
            // INTENTO 1: Buscar si es un EMPLEADO
            // ==========================================
            try {
                Long idLong = Long.parseLong(idLimpio);
                Usuario emp = usuarioRepo.findByIdentificacionOptimized(idLong).orElse(null);
                
                if (emp != null && contrasena.equals(emp.getContrasena())) {
                    Integer idRol = usuarioRepo.obtenerRolPorIdentificacion(emp.getIdentificacion());

                    if (idRol != null) {
                        session.setAttribute("usuarioSesion", emp);
                        session.setAttribute("tipoUsuario", "EMPLEADO");
                        session.setAttribute("rolSesion", idRol);

                        System.out.println("LOG: Login exitoso Empleado. Rol: " + idRol);
                        return switch (idRol) {
                            case 1 -> "redirect:/admin_dashboard";
                            case 2 -> "redirect:/supervisor/dashboard"; 
                            default -> "redirect:/usuario_dashboard";
                        };
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("LOG: La cédula contiene letras o guiones, buscando solo en clientes.");
            }

            // ==========================================
            // INTENTO 2: Buscar si es un CLIENTE
            // ==========================================
            Cliente cli = clienteRepo.findById(idLimpio).orElse(null);
            
            if (cli != null && contrasena.equals(cli.getContrasena())) {
                // CORRECCIÓN CLAVE: Guardarlo como "clienteSesion" para que el Controller del cliente lo encuentre
                session.setAttribute("clienteSesion", cli); 
                session.setAttribute("tipoUsuario", "CLIENTE");
                
                System.out.println("LOG: Login exitoso Cliente. Cédula: " + cli.getCedula());
                return "redirect:/cliente_dashboard";
            }

            // Si llega aquí, es porque no existe o la contraseña está mal
            System.out.println("LOG: Credenciales incorrectas para el ID: " + idLimpio);
            return "redirect:/?error=1";

        } catch (Exception e) {
            System.err.println("ERROR CRÍTICO EN LOGIN:");
            e.printStackTrace(); // Esto nos dirá exactamente la línea del error en la consola
            return "redirect:/?error=1";
        }
    }

    // --- PROCESO DE REGISTRO (Solo para Clientes) ---
    @PostMapping("/registrar")
    public String registrarCliente(
            @RequestParam String cedula,
            @RequestParam String nombre_usuario,
            @RequestParam String apellido_paterno,
            @RequestParam String apellido_materno,
            @RequestParam Integer id_tipo_cliente,
            @RequestParam String contrasena) {

        try {
            if (clienteRepo.findById(cedula).isPresent()) {
                System.out.println("DEBUG: El cliente con ID " + cedula + " ya existe. Saltando alerta.");
                return "redirect:/?error_registro=duplicado";
            }

            clienteRepo.insertarClienteSP(
                    cedula,
                    nombre_usuario,
                    apellido_paterno,
                    apellido_materno,
                    id_tipo_cliente,
                    contrasena,
                    1);

            return "redirect:/?registrado=1";

        } catch (Exception e) {
            System.err.println("ERROR CRÍTICO: " + e.getMessage());
            return "redirect:/?error_registro=1";
        }
    }

    // --- DASHBOARDS PROTEGIDOS ---

    @GetMapping("/admin_dashboard")
public String dashboardAdmin(HttpSession session, Model model) {
    if (!validarAcceso(session, 1)) return "redirect:/";
        
    Usuario user = (Usuario) session.getAttribute("usuarioSesion");
    
    try {
        String nombreCompleto = dashboardRepo.obtenerNombrePorIdentificacion(user.getIdentificacion());
        model.addAttribute("nombreAdmin", (nombreCompleto != null && !nombreCompleto.isEmpty()) ? nombreCompleto : user.getNombre());

        // --- LO QUE TE FALTA PARA QUE LOS SELECTS SIRVAN ---
        // Asegúrate de tener estos repositorios inyectados con @Autowired arriba
        model.addAttribute("materiales", materialRepo.findAll()); 
        
        // --------------------------------------------------

        model.addAttribute("extraccionHoy", dashboardRepo.produccionHoy());
        model.addAttribute("maquinasActivas", dashboardRepo.contarMaquinariaActiva());
        model.addAttribute("horasMaquina", dashboardRepo.contarHorasMaquinaria());
        model.addAttribute("totalPlanillas", dashboardRepo.totalPlanillas());
        model.addAttribute("totalDeducciones", dashboardRepo.totalDeducciones());
        model.addAttribute("usuariosDia", dashboardRepo.contarUsuariosTurno(1L));
        model.addAttribute("usuariosNoche", dashboardRepo.contarUsuariosTurno(2L));

    } catch (Exception e) {
        System.err.println("Error en Dashboard: " + e.getMessage());
        model.addAttribute("nombreAdmin", user.getNombre());
    }

    return "admin_dashboard";
}

    @GetMapping("/supervisor_dashboard")
    public String dashboardSupervisor(HttpSession session, Model model) {
        if (!validarAcceso(session, 2))
            return "redirect:/";
       
        // model.addAttribute("nombre", user.getNombreUsuario()); // Ajusta si es necesario
        return "redirect:/supervisor/dashboard";
    }

    @GetMapping("/usuario_dashboard")
    public String dashboardUsuario(HttpSession session, Model model) {
        if (!validarAcceso(session, 3))
            return "redirect:/";
        
        // model.addAttribute("nombre", user.getNombreUsuario()); // Ajusta si es necesario
        return "usuario_dashboard";
    }

    

    private boolean validarAcceso(HttpSession session, int rolRequerido) {
        Object user = session.getAttribute("usuarioSesion");
        Integer rolSesion = (Integer) session.getAttribute("rolSesion");
        String tipo = (String) session.getAttribute("tipoUsuario");

        if (user == null || !"EMPLEADO".equals(tipo) || rolSesion == null) {
            return false;
        }

        return rolSesion == rolRequerido;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}