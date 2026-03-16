package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Cliente;
import com.tajo.proyecto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteAdminController {

    @Autowired
    private ClienteRepository clienteRepository;

    // --- LISTAR CLIENTES ---
    @GetMapping
    public String mostrarGestionClientes(Model model, HttpSession session) {
        Object emp = session.getAttribute("usuarioSesion");
        if (emp == null) return "redirect:/";

        try {
            List<Cliente> lista = clienteRepository.findAll();
            model.addAttribute("listaClientes", lista);
            model.addAttribute("usuario", emp);
        } catch (Exception e) {
            System.err.println("ERROR Oracle al listar: " + e.getMessage());
        }

        return "admin/clientes/gestion_clientes";
    }

    // --- INSERTAR CLIENTE (Usando SP del Paquete) ---
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        try {
            clienteRepository.insertarClienteSP(
                cliente.getCedula(),
                cliente.getNombre(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getIdTipoCliente(),
                cliente.getContrasena(),
                cliente.getIdEstado()
            );
            System.out.println("LOG: Cliente insertado correctamente vía SP");
        } catch (Exception e) {
            System.err.println("Error en SP Insertar: " + e.getMessage());
        }
        return "redirect:/admin/clientes";
    }

    // --- MODIFICAR CLIENTE (Usando SP del Paquete) ---
    @PostMapping("/editar")
    public String editarCliente(@ModelAttribute("cliente") Cliente cliente) {
        try {
            clienteRepository.modificarClienteSP(
                cliente.getCedula(),
                cliente.getNombre(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getIdTipoCliente()
            );
            System.out.println("LOG: Cliente modificado correctamente");
        } catch (Exception e) {
            System.err.println("Error en SP Modificar: " + e.getMessage());
        }
        return "redirect:/admin/clientes";
    }

    // --- ELIMINAR CLIENTE (Borrado Lógico usando SP) ---
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") String id, HttpSession session) {
        if (session.getAttribute("usuarioSesion") == null) return "redirect:/";

        try {
            // Usamos el SP que cambia el estado a 2 (Inactivo)
            clienteRepository.eliminarClienteSP(id);
            System.out.println("LOG: Cliente inactivado correctamente");
        } catch (Exception e) {
            System.err.println("Error en SP Eliminar: " + e.getMessage());
        }
        return "redirect:/admin/clientes";
    }
}