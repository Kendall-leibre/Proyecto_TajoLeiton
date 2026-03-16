package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Transporte;
import com.tajo.proyecto.model.Usuario;
import com.tajo.proyecto.repository.EstadoRepository;
import com.tajo.proyecto.repository.MaterialRepository;
import com.tajo.proyecto.repository.TransporteRepository;
import com.tajo.proyecto.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin/transporte")
public class TransporteController {

    @Autowired
    private TransporteRepository transporteRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private MaterialRepository materialRepo;
    @Autowired
    private EstadoRepository estadoRepo;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listaTransportes", transporteRepo.findAll());
        model.addAttribute("listaUsuarios", usuarioRepo.findAll()); // Para los select de conductores
        model.addAttribute("listaMateriales", materialRepo.findAll()); // Para los select de materiales
        model.addAttribute("listaEstados", estadoRepo.findAll()); // Para los estados
        return "admin/transporte/gestion_transporte";
    }

    @PostMapping("/guardar")
    public String guardarTransporte(@ModelAttribute("trans") Transporte trans, RedirectAttributes flash,
            HttpSession session) {
        // Detectamos el rol para saber a dónde regresar
        Usuario user = (Usuario) session.getAttribute("usuarioSesion");
        boolean esSupervisor = user != null && user.getRoles().stream().anyMatch(r -> r.getIdRol() == 2);
        String rutaRetorno = esSupervisor ? "redirect:/supervisor/transporte" : "redirect:/admin/transporte";
        
        if (transporteRepo.existsById(trans.getIdTransporte())) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error: El ID " + trans.getIdTransporte() + " ya existe.");
            return rutaRetorno;
        }

        try {
            BigDecimal estadoBD = (trans.getIdEstado() != null) ? new BigDecimal(trans.getIdEstado().toString())
                    : BigDecimal.ONE;

            transporteRepo.insertarTransporte(
                    trans.getIdTransporte(),
                    trans.getPlaca(),
                    trans.getIdentificacion(),
                    trans.getIdMaterial(),
                    trans.getCantidadTransportada(),
                    trans.getPrecioTransporte(),
                    trans.getDestino(),
                    estadoBD);

            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "¡Transporte registrado con éxito!");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al registrar: " + e.getMessage());
        }
        return rutaRetorno;
    }

    @PostMapping("/editar")
    public String editarTransporte(@ModelAttribute("trans") Transporte trans, RedirectAttributes flash) {
        try {
            transporteRepo.modificarTransporte(
                    trans.getIdTransporte(),
                    trans.getPlaca(),
                    trans.getIdentificacion(),
                    trans.getCantidadTransportada(),
                    trans.getPrecioTransporte(),
                    trans.getDestino());
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Transporte actualizado sin problemas.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Fallo al actualizar en Oracle: " + e.getMessage());
        }
        return "redirect:/admin/transporte";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTransporte(@PathVariable Long id, RedirectAttributes flash) {
        try {
            transporteRepo.eliminarTransporte(id);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "El transporte fue inactivado exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "No se pudo inactivar el registro: " + e.getMessage());
        }
        return "redirect:/admin/transporte";
    }
}