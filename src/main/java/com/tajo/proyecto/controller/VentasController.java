package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Venta;
import com.tajo.proyecto.model.DetalleVenta;
import com.tajo.proyecto.model.DetalleVentaId;
import com.tajo.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin/comercial") // ¡Hicimos caso a tu lógica, crack!
public class VentasController {

    @Autowired private VentaRepository ventaRepo;
    @Autowired private DetalleVentaRepository detalleVentaRepo;
    @Autowired private UsuarioRepository usuarioRepo; 
    @Autowired private MaterialRepository materialRepo;
    @Autowired private EstadoRepository estadoRepo;
    @Autowired private FacturacionRepository facturacionRepo; 
    @Autowired private DescuentoRepository descuentoRepo;
    @Autowired private ImpuestoRepository impuestoRepo;
    @Autowired private MetodoPagoRepository metodoPagoRepo;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listaVentas", ventaRepo.findAll());
        model.addAttribute("listaDetalles", detalleVentaRepo.findAll());
        model.addAttribute("listaFacturas", facturacionRepo.findAll()); 
        model.addAttribute("listaUsuarios", usuarioRepo.findAll());
        model.addAttribute("listaMateriales", materialRepo.findAll());
        model.addAttribute("listaEstados", estadoRepo.findAll());
        
        // LOS 3 NUEVOS PARA FACTURACIÓN
        model.addAttribute("listaDescuentos", descuentoRepo.findAll());
        model.addAttribute("listaImpuestos", impuestoRepo.findAll());
        model.addAttribute("listaMetodos", metodoPagoRepo.findAll());

        return "admin/comercial/gestion_ventas_facturacion";
    }

    // ==========================================
    // MÓDULO: VENTA PRINCIPAL (MAESTRO)
    // ==========================================

    @PostMapping("/ventas/guardar")
    public String guardarVenta(@ModelAttribute("venta") Venta venta, RedirectAttributes flash) {
        if (ventaRepo.existsById(venta.getIdVenta())) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error: El ID de Venta " + venta.getIdVenta() + " ya existe.");
            return "redirect:/admin/comercial";
        }
        try {
            BigDecimal estadoBD = (venta.getIdEstado() != null) ? new BigDecimal(venta.getIdEstado().toString()) : BigDecimal.ONE;
            ventaRepo.insertarVenta(venta.getIdVenta(), venta.getCedula(), venta.getIdMaterial(), venta.getCantidadVenta(), venta.getPrecio(), venta.getFechaVenta(), estadoBD);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Encabezado de venta registrado exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al registrar la venta: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }

    @PostMapping("/ventas/editar")
    public String editarVenta(@ModelAttribute("venta") Venta venta, RedirectAttributes flash) {
        try {
            ventaRepo.modificarVenta(venta.getIdVenta(), venta.getIdMaterial(), venta.getCantidadVenta(), venta.getPrecio());
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Venta actualizada correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al actualizar la venta: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }

    @GetMapping("/ventas/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id, RedirectAttributes flash) {
        try {
            ventaRepo.eliminarVenta(id);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Venta anulada correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al anular la venta: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }

    // ==========================================
    // MÓDULO: DETALLE DE VENTA (LÍNEAS)
    // ==========================================

    @PostMapping("/detalle/guardar")
    public String guardarDetalle(@ModelAttribute("detalle") DetalleVenta detalle, RedirectAttributes flash) {
        DetalleVentaId idCompuesto = new DetalleVentaId();
        idCompuesto.setIdVenta(detalle.getIdVenta());
        idCompuesto.setIdMaterial(detalle.getIdMaterial());

        if (detalleVentaRepo.existsById(idCompuesto)) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error: Ya existe ese material en esta venta.");
            return "redirect:/admin/comercial";
        }
        try {
            BigDecimal estadoBD = (detalle.getIdEstado() != null) ? new BigDecimal(detalle.getIdEstado().toString()) : BigDecimal.ONE;
            detalleVentaRepo.insertarDetalleVenta(detalle.getIdVenta(), detalle.getIdMaterial(), detalle.getPrecioTotal(), estadoBD);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Producto agregado a la venta correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al agregar producto: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }

    @PostMapping("/detalle/editar")
    public String editarDetalle(@ModelAttribute("detalle") DetalleVenta detalle, RedirectAttributes flash) {
        try {
            detalleVentaRepo.modificarDetalleVenta(detalle.getIdVenta(), detalle.getIdMaterial(), detalle.getPrecioTotal());
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Detalle de venta actualizado.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al actualizar detalle: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }

    @GetMapping("/detalle/eliminar/{idVenta}/{idMaterial}")
    public String eliminarDetalle(@PathVariable Long idVenta, @PathVariable Long idMaterial, RedirectAttributes flash) {
        try {
            detalleVentaRepo.eliminarDetalleVenta(idVenta, idMaterial);
            flash.addFlashAttribute("tipo", "success");
            flash.addFlashAttribute("mensaje", "Producto removido de la venta.");
        } catch (Exception e) {
            flash.addFlashAttribute("tipo", "error");
            flash.addFlashAttribute("mensaje", "Error al remover producto: " + e.getMessage());
        }
        return "redirect:/admin/comercial";
    }
}