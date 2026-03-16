package com.tajo.proyecto.controller;

import com.tajo.proyecto.model.Material;
import com.tajo.proyecto.repository.InventarioRepository;
import com.tajo.proyecto.repository.MaterialRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/materiales")
public class MaterialAdminController {

    @Autowired
    private MaterialRepository materialRepo;

    @Autowired
    private InventarioRepository inventarioRepo; 

    @GetMapping
    public String listar(Model model, HttpSession session) {
        // Validación de sesión
        if (session.getAttribute("usuarioSesion") == null) return "redirect:/";
        
        try {
            // 2. PASAMOS AMBAS LISTAS
            model.addAttribute("listaMateriales", materialRepo.findAll());
            model.addAttribute("listaInventario", inventarioRepo.findAll());
            
            // Pasamos el usuario para el header
            model.addAttribute("usuario", session.getAttribute("usuarioSesion"));
            
        } catch (Exception e) {
            // Esto es para que si falla la base, veas el error en consola y no solo el 500
            System.err.println("Error al cargar datos: " + e.getMessage());
            model.addAttribute("error", "Error al conectar con Oracle");
        }

        return "admin/clientes/gestion_materiales";
    }


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Material mat) {
        try {
            materialRepo.insertarMaterialSP(
                    mat.getIdMaterial(),
                    mat.getNombre(),
                    mat.getUnidadMedida(),
                    mat.getFuncion(),
                    mat.getIdEstado());
        } catch (Exception e) {
            System.err.println("Error SP Insertar Material: " + e.getMessage());
        }
        return "redirect:/admin/materiales";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute Material mat) {
        materialRepo.modificarMaterialSP(
                mat.getIdMaterial(),
                mat.getNombre(),
                mat.getUnidadMedida(),
                mat.getFuncion(),
                mat.getIdEstado() 
        );
        return "redirect:/admin/materiales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            materialRepo.eliminarMaterialSP(id);
        } catch (Exception e) {
            System.err.println("Error SP Eliminar Material: " + e.getMessage());
        }
        return "redirect:/admin/materiales";
    }
}