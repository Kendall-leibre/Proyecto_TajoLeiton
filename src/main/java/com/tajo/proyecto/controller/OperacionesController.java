package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Extraccion;
import com.tajo.proyecto.model.Produccion;
import com.tajo.proyecto.repository.*;
import com.tajo.proyecto.model.Usuario;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/operaciones")
public class OperacionesController {

    @Autowired
    private ExtraccionRepository extraccionRepo;
    @Autowired
    private ProduccionRepository produccionRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private MaterialRepository materialRepo;
    @Autowired
    private EstadoRepository estadoRepo;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listaExtracciones", extraccionRepo.findAll());
        model.addAttribute("listaProducciones", produccionRepo.findAll());
        model.addAttribute("listaUsuarios", usuarioRepo.findAll());
        model.addAttribute("listaMateriales", materialRepo.findAll());
        model.addAttribute("listaEstados", estadoRepo.findAll());
        return "admin/operaciones/gestion_operaciones";
    }

    @PostMapping("/extraccion/guardar")
    public String guardarExtraccion(@ModelAttribute Extraccion ext, RedirectAttributes redirectAttrs,
            HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("usuarioSesion");
        boolean esSupervisor = user != null && user.getRoles().stream().anyMatch(r -> r.getIdRol() == 2);
        String rutaRetorno = esSupervisor ? "redirect:/supervisor/operaciones" : "redirect:/admin/operaciones";

        // Verificamos si ya existe el ID de extracción
        if (extraccionRepo.existsById(ext.getIdExtraccion())) {
            redirectAttrs.addFlashAttribute("tipo", "danger");
            redirectAttrs.addFlashAttribute("mensaje",
                    "Error: El ID de Extracción " + ext.getIdExtraccion() + " ya existe.");
            return rutaRetorno;
        }

        try {
            extraccionRepo.insertarExtraccion(
                    ext.getIdExtraccion(),
                    ext.getIdentificacion(),
                    ext.getIdMaterial(),
                    ext.getFechaExtraccion(),
                    ext.getIdEstado());
            redirectAttrs.addFlashAttribute("tipo", "success");
            redirectAttrs.addFlashAttribute("mensaje", "¡Salida de material registrada con éxito!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("tipo", "danger");
            redirectAttrs.addFlashAttribute("mensaje", "Error al registrar salida: " + e.getMessage());
        }
        return rutaRetorno;
    }

    @PostMapping("/extraccion/editar")
    public String editarExtraccion(@ModelAttribute Extraccion ext) {
        try {
            extraccionRepo.modificarExtraccion(
                    ext.getIdExtraccion(),
                    ext.getIdentificacion(),
                    ext.getIdMaterial(),
                    ext.getFechaExtraccion(),
                    ext.getIdEstado());
            return "redirect:/admin/operaciones?success";
        } catch (Exception e) {
            return "redirect:/admin/operaciones?error=" + e.getMessage();
        }
    }

    @PostMapping("/produccion/guardar")
    public String guardarProduccion(@ModelAttribute Produccion prod, RedirectAttributes redirectAttrs,
            HttpSession session) {
        Usuario user = (Usuario) session.getAttribute("usuarioSesion");
        boolean esSupervisor = user != null && user.getRoles().stream().anyMatch(r -> r.getIdRol() == 2);
        String rutaRetorno = esSupervisor ? "redirect:/supervisor/operaciones" : "redirect:/admin/operaciones";

        // VALIDACIÓN PROFESIONAL DE LLAVE
        boolean existe = produccionRepo.findAll().stream()
                .anyMatch(p -> p.getIdProduccion().equals(prod.getIdProduccion())
                        && p.getIdMaterial().equals(prod.getIdMaterial()));

        if (existe) {
            redirectAttrs.addFlashAttribute("tipo", "danger"); // Color rojo
            redirectAttrs.addFlashAttribute("mensaje", "Error: Ya existe un registro de producción con el ID "
                    + prod.getIdProduccion() + " para ese material.");
            return rutaRetorno;
        }

        try {
            produccionRepo.insertarProduccion(
                    prod.getIdProduccion(),
                    prod.getIdMaterial(),
                    prod.getCantidad(),
                    prod.getFechaProduccion(),
                    new java.math.BigDecimal("1") // Estado activo por defecto
            );
            redirectAttrs.addFlashAttribute("tipo", "success");
            redirectAttrs.addFlashAttribute("mensaje", "¡Producción registrada exitosamente en el sistema!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("tipo", "danger");
            redirectAttrs.addFlashAttribute("mensaje", "Error en el sistema: " + e.getMessage());
        }
        return rutaRetorno;
    }

    @PostMapping("/produccion/editar")
    public String editarProduccion(@ModelAttribute("prod") Produccion prod, RedirectAttributes redirectAttrs) {
        try {
            produccionRepo.modificarProduccion(
                    prod.getIdProduccion(),
                    prod.getIdMaterial(), // <--- Ahora sí enviamos el material
                    prod.getCantidad(),
                    prod.getFechaProduccion(),
                    new BigDecimal(prod.getIdEstado().toString()));
            redirectAttrs.addFlashAttribute("tipo", "success");
            redirectAttrs.addFlashAttribute("mensaje", "Producción de TAJO actualizada con éxito.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("tipo", "error");
            redirectAttrs.addFlashAttribute("mensaje", "Error: " + e.getMessage());
        }
        return "redirect:/admin/operaciones";
    }

    @GetMapping("/extraccion/eliminar/{id}")
    public String eliminarExtraccion(@PathVariable Long id) {
        try {
            extraccionRepo.eliminarExtraccion(id);
            return "redirect:/admin/operaciones?success";
        } catch (Exception e) {
            return "redirect:/admin/operaciones?error=" + e.getMessage();
        }
    }

    @GetMapping("/produccion/eliminar/{id}")
    public String eliminarProduccion(@PathVariable Long id) {
        try {
            produccionRepo.eliminarProduccion(id);
            return "redirect:/admin/operaciones?success";
        } catch (Exception e) {
            return "redirect:/admin/operaciones?error=" + e.getMessage();
        }
    }
}