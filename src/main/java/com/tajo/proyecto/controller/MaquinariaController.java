package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Maquinaria;
import com.tajo.proyecto.repository.EstadoRepository;
import com.tajo.proyecto.repository.MaquinariaRepository;
import com.tajo.proyecto.repository.TipoMaquinariaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin/maquinaria")
public class MaquinariaController {

    @Autowired
    private MaquinariaRepository maquinariaRepo;

    @Autowired
    private TipoMaquinariaRepository tipoRepo;

    @Autowired
    private EstadoRepository estadoRepo;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioSesion") == null) {
            return "redirect:/";
        }
        model.addAttribute("listaMaquinaria", maquinariaRepo.findAll());
        model.addAttribute("listaTipos", tipoRepo.findAll());
        model.addAttribute("listaEstados", estadoRepo.findAll());
        model.addAttribute("usuario", session.getAttribute("usuarioSesion"));

        return "admin/maquinaria/gestion_maquinaria";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Maquinaria maq, @RequestParam("idTipo") BigDecimal idTipo, RedirectAttributes flash) {
        try {
            // USANDO EL SP DE INSERTAR
            // Nota: El SP de Oracle debería encargarse de insertar en ambas tablas (MAQUINARIA y TIPO_MAQUINARIA)
            // Si tu SP solo inserta en una, asegúrate de que la lógica de negocio esté en Oracle.
            maquinariaRepo.insertarMaquinaria(
                maq.getPlaca(),
                maq.getModelo(),
                idTipo, // Pasamos el tipo
                maq.getIdEstado()
            );
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Maquinaria registrada correctamente vía Oracle.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al insertar: " + e.getMessage());
        }
        return "redirect:/admin/maquinaria";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute Maquinaria maq, RedirectAttributes flash) {
        try {
            // USANDO EL SP DE MODIFICAR
            // Pasamos los parámetros que definiste en tu SP de Oracle
            maquinariaRepo.modificarMaquinaria(
                maq.getPlaca(),
                maq.getModelo(),
                maq.getHorasTrabajadas(),
                maq.getIdEstado()
            );
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Unidad actualizada exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Fallo en la actualización: " + e.getMessage());
        }
        return "redirect:/admin/maquinaria";
    }

    @GetMapping("/eliminar/{placa}")
    public String eliminar(@PathVariable BigDecimal placa, RedirectAttributes flash) {
        try {
            // USANDO EL SP DE ELIMINAR (Inactivación Lógica)
            // Este es el método que ya limpia todas las tablas relacionadas en Oracle
            maquinariaRepo.eliminarMaquinaria(placa);
            
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "La unidad ha sido inactivada por el sistema.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/admin/maquinaria";
    }
}