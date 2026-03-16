package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Cliente;
import com.tajo.proyecto.repository.ClienteDashboardRepository;
import com.tajo.proyecto.repository.MaterialRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date; 

@Controller
public class ClienteController {

    @Autowired
    private ClienteDashboardRepository clienteDashRepo;

    @Autowired
    private MaterialRepository materialRepo;

    @GetMapping("/cliente_dashboard")
    public String dashboardCliente(HttpSession session, Model model) {
        Cliente clienteSesion = (Cliente) session.getAttribute("clienteSesion");
        
        if (clienteSesion == null) {
            return "redirect:/?error=nologin";
        }

        String cedula = clienteSesion.getCedula(); 

        try {
            model.addAttribute("nombreCliente", clienteSesion.getNombre()); 
            model.addAttribute("totalComprado", clienteDashRepo.totalCompradoPorCliente(cedula));
            model.addAttribute("ventasActivas", clienteDashRepo.ventasActivasCliente(cedula));
            model.addAttribute("materialesDisponibles", materialRepo.findAll());

        } catch (Exception e) {
            System.err.println("Error cargando dashboard de cliente: " + e.getMessage());
            model.addAttribute("nombreCliente", clienteSesion.getNombre());
        }

        // ---> CAMBIO AQUÍ: Ahora busca en la carpeta "cliente" <---
        return "cliente/cliente_dashboard"; 
    }

    @GetMapping("/cliente/comprar")
    public String pantallaCompra(@RequestParam Long idMaterial, HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteSesion");
        if (cliente == null) return "redirect:/?error=nologin";

        model.addAttribute("material", materialRepo.findById(idMaterial).orElse(null));
        model.addAttribute("nombreCliente", cliente.getNombre());
        
        // ---> CAMBIO AQUÍ: Ahora busca en la carpeta "cliente" <---
        return "cliente/cliente_nueva_compra";
    }

    @PostMapping("/cliente/procesar_compra")
    public String procesarCompra(
            @RequestParam Long idMaterial,
            @RequestParam Double cantidad,
            @RequestParam Double precioTotal,
            HttpSession session) {
        
        Cliente cliente = (Cliente) session.getAttribute("clienteSesion");
        if (cliente == null) return "redirect:/?error=nologin";

        try {
            Long cedulaLong = Long.parseLong(cliente.getCedula());
            Long nuevoIdVenta = clienteDashRepo.obtenerSiguienteIdVenta();
            
            Date fechaHoy = new Date(System.currentTimeMillis());
            Integer estadoPendiente = 1;

            clienteDashRepo.registrarNuevaVenta(
                nuevoIdVenta, 
                cedulaLong, 
                idMaterial, 
                cantidad, 
                precioTotal, 
                fechaHoy, 
                estadoPendiente
            );
            
            // Los redirect NO cambian porque apuntan a la URL, no a la carpeta
            return "redirect:/cliente_dashboard?exito=1";
            
        } catch (Exception e) {
            System.err.println("Error al registrar venta con SP: " + e.getMessage());
            return "redirect:/cliente_dashboard?error_compra=1";
        }
    }
}