package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Usuario;
import com.tajo.proyecto.repository.UsuarioRepository;
import com.tajo.proyecto.repository.MaquinariaRepository;
import com.tajo.proyecto.repository.RolRepository;
import com.tajo.proyecto.repository.EstadoRepository;
import com.tajo.proyecto.repository.TurnoRepository; // Importar
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private MaquinariaRepository maquinariaRepo;

    @Autowired
    private EstadoRepository estadoRepo;

    @Autowired
    private TurnoRepository turnoRepo; // Inyectar

    @Autowired
    private RolRepository rolRepo; // Inyectar el repositorio de Roles para guardar el

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioSesion") == null) return "redirect:/";

        // Traemos todo de la base de datos para los combos
        model.addAttribute("listaUsuarios", usuarioRepo.findAll());
        model.addAttribute("listaMaquinaria", maquinariaRepo.findAll());
        model.addAttribute("listaEstados", estadoRepo.findAll());
        model.addAttribute("listaTurnos", turnoRepo.findAll()); // Lista de Turnos
        model.addAttribute("listaRolesUnicos", rolRepo.findDistinctNombreRol());
        
        model.addAttribute("usuario", session.getAttribute("usuarioSesion"));
        return "admin/usuarios/gestion_usuarios";
    }

    @PostMapping("/guardar")
public String guardar(@ModelAttribute Usuario user, 
                      @RequestParam("idRolSeleccionado") Long idRol,
                      @RequestParam("nombreRolSeleccionado") String nombreRol,
                      org.springframework.web.servlet.mvc.support.RedirectAttributes flash) {
    try {
        // 1. Insertar en la tabla de Usuarios
        usuarioRepo.insertarUsuario(
            user.getIdentificacion(),
            user.getNombre(),
            user.getApellidoPaterno(),
            user.getApellidoMaterno(),
            user.getEdad(),
            new java.sql.Date(user.getFechaIngreso().getTime()),
            user.getIdTurno(),
            user.getPlaca(),
            user.getIdEstado() != null ? user.getIdEstado() : new java.math.BigDecimal(1),
            user.getContrasena()
        );

        // 2. Insertar en la tabla de Roles (usando el SP que me pasaste)
        rolRepo.insertarRol(
            user.getIdentificacion(),
            idRol,
            nombreRol,
            1 // Estado activo por defecto para el rol
        );

        flash.addFlashAttribute("success", "Operador registrado correctamente en el sistema.");
        
    } catch (Exception e) {
        // Si Oracle detecta que la cédula ya existe, lanza excepción y cae aquí
        flash.addFlashAttribute("error", "¡Atención! La identificación " + user.getIdentificacion() + " ya está registrada.");
    }
    return "redirect:/admin/usuarios";
}
    @PostMapping("/editar")
public String editar(@ModelAttribute Usuario user, 
                     @RequestParam("idRolSeleccionado") Long idRol,
                     @RequestParam("nombreRolSeleccionado") String nombreRol,
                     RedirectAttributes flash) {
    try {
        // AQUÍ ESTÁ EL TRUCO: Convertir a BigDecimal para que el Repo no llore
        usuarioRepo.modificarUsuario(
            user.getIdentificacion(),
            user.getNombre(),
            user.getApellidoPaterno(),
            user.getApellidoMaterno(),
            user.getEdad(),
            user.getIdTurno(), 
            user.getPlaca(),    
            user.getIdEstado()  
        );

        // También modificamos el rol con el SP que me pasaste antes
        rolRepo.modificarRol(user.getIdentificacion(), nombreRol);

        flash.addFlashAttribute("success", "Operador actualizado correctamente.");
    } catch (Exception e) {
        flash.addFlashAttribute("error", "Error al modificar: " + e.getMessage());
        e.printStackTrace(); // Para que veas el error real en la consola
    }
    return "redirect:/admin/usuarios";
}

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            usuarioRepo.eliminarUsuario(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
}