package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Usuario;
import com.tajo.proyecto.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DashboardRepository dashboardRepo;
    @Autowired
    private ProduccionRepository produccionRepo;
    @Autowired
    private ExtraccionRepository extraccionRepo;
    @Autowired
    private EstadoRepository estadoRepo;
    @Autowired
    private MaquinariaRepository maquinariaRepo;
    @Autowired
    private TransporteRepository transporteRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private MaterialRepository materialRepo;

    // Repositorio para jalar los empleados

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuarioSesion");
        if (user == null)
            return "redirect:/";

        // CARGAR LOS DATOS PARA LA TABLA CENTRAL
        model.addAttribute("listaPersonal", usuarioRepo.findAllOptimized());

        // CARGAR LOS CONTADORES (Asegúrate de que estos nombres coincidan con el HTML)
        model.addAttribute("nombre", user.getNombre());
        model.addAttribute("extraccionHoy", dashboardRepo.produccionHoy());
        model.addAttribute("maquinasActivas", dashboardRepo.contarMaquinariaActiva());
        model.addAttribute("horasMaquina", dashboardRepo.contarHorasMaquinaria());

        return "supervisor_dashboard";
    }

    @Transactional(readOnly = true)
@GetMapping("/operaciones")
public String operaciones(Model model) {
    // 1. Limpiamos el contexto para asegurar datos frescos de Oracle
    entityManager.clear();

    // 2. CAMBIO CLAVE: Usamos los métodos con JOIN FETCH
    // En lugar de findAll(), usamos los que traen relaciones de un solo golpe
    model.addAttribute("listaProducciones", produccionRepo.findAllOptimized()); 
    model.addAttribute("listaExtracciones", extraccionRepo.findAllOptimized());

    // 3. Consultas de apoyo (Materiales y Estados)
    model.addAttribute("materiales", dashboardRepo.obtenerMaterialesSimple());
    model.addAttribute("listaEstados", estadoRepo.findAll());

    return "supervisor/operaciones/gestion_operaciones";
}

    // --- NUEVO MÉTODO PARA TRANSPORTE ---
    @Transactional(readOnly = true)
    @GetMapping("/transporte")
    public String transporte(Model model) {
        entityManager.clear(); // Para ver datos frescos de Oracle

        model.addAttribute("listaTransportes", transporteRepo.findAll());
        model.addAttribute("listaUsuarios", usuarioRepo.findAll()); // Para el select de choferes
        model.addAttribute("listaMateriales", materialRepo.findAll()); // Para el select de materiales
        model.addAttribute("listaEstados", estadoRepo.findAll()); // Para el select de estados
        model.addAttribute("listaMaquinaria", maquinariaRepo.findAll());

        // Asegúrate de que la ruta sea esta en tus carpetas:
        return "supervisor/transporte/gestion_transporte";
    }

    @GetMapping("/maquinaria")
    public String maquinaria(Model model) {
        entityManager.clear();
        model.addAttribute("listaMaquinaria", maquinariaRepo.findAll());
        model.addAttribute("listaEstados", estadoRepo.findAll());
        return "supervisor/maquinaria/gestion_maquinaria";
    }

    @GetMapping("/maquinaria/inactivar/{placa}")
    public String inactivarMaquinaria(@PathVariable BigDecimal placa, RedirectAttributes flash) {
        try {
            // LLAMADA ESTRICTA AL SP
            maquinariaRepo.eliminarMaquinaria(placa);

            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Unidad [" + placa + "] enviada a mantenimiento exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Fallo al inactivar en Oracle: " + e.getMessage());
        }
        // Redirección segura al panel del supervisor
        return "redirect:/supervisor/maquinaria";
    }
}