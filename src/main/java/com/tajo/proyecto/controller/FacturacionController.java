package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Factura;
import com.tajo.proyecto.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin/facturacion")
public class FacturacionController {

    @Autowired
    private FacturacionRepository facturacionRepo;

    // ELIMINAMOS EL MÉTODO INDEX PORQUE LA VISTA AHORA LA MANEJA EL
    // VENTASCONTROLLER

    @PostMapping("/guardar")
    public String guardarFactura(@ModelAttribute("factura") Factura factura, RedirectAttributes flash) {
        if (facturacionRepo.existsById(factura.getIdFactura())) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje",
                    "Error: El ID de Factura " + factura.getIdFactura() + " ya se encuentra registrado en el sistema.");
            return "redirect:/admin/comercial"; // Redirige al panel unificado
        }

        try {
            BigDecimal estadoBD = (factura.getIdEstado() != null)
                    ? new BigDecimal(factura.getIdEstado().toString())
                    : BigDecimal.ONE;

            facturacionRepo.insertarFactura(
                    factura.getIdFactura(),
                    factura.getCedula(),
                    factura.getIdVenta(),
                    factura.getIdImpuesto(),
                    factura.getIdDescuento(),
                    factura.getIdMetodoPago(),
                    factura.getFechaEmision(),
                    factura.getMontoTotal(),
                    estadoBD);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "El registro de la factura se procesó correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error interno al registrar la factura: " + e.getMessage());
        }
        return "redirect:/admin/comercial"; // Redirige al panel unificado
    }

    @PostMapping("/editar")
    public String editarFactura(@ModelAttribute("factura") Factura factura, RedirectAttributes flash) {
        try {
            // Convertimos el estado de forma segura
            BigDecimal estadoBD = (factura.getIdEstado() != null) ? new BigDecimal(factura.getIdEstado().toString())
                    : BigDecimal.ONE;

            facturacionRepo.modificarFactura(
                    factura.getIdFactura(),
                    factura.getIdMetodoPago(),
                    factura.getMontoTotal(),
                    estadoBD // Enviamos el estado a Oracle
            );
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "La factura ha sido actualizada exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Fallo al actualizar la base de datos: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarFactura(@PathVariable Long id, RedirectAttributes flash) {
        try {
            facturacionRepo.eliminarFactura(id);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "La factura seleccionada ha sido anulada.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "No se pudo anular el registro: " + e.getMessage());
        }
        return "redirect:/admin/comercial"; // Redirige al panel unificado
    }
}