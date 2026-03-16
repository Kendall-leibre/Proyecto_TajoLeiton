package com.tajo.proyecto.controller;

import com.tajo.proyecto.repository.DashboardRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {

    @Autowired
    private DashboardRepository dashboardRepo;

    @GetMapping("/nombre-usuario")
    public String getNombre(@RequestParam Long cedula) {
        String nombre = dashboardRepo.obtenerNombrePorIdentificacion(cedula);
        return (nombre != null) ? nombre : "Usuario no encontrado";
    }

    @GetMapping("/stock-material")
    public ResponseEntity<String> getStock(@RequestParam Long idMaterial) {
        BigDecimal stock = dashboardRepo.stockActualMaterial(idMaterial);
        // Devolvemos el string para que el JS no se confunda con formatos científicos
        return ResponseEntity.ok(stock.toPlainString());
    }

@GetMapping("/clientes-tipo")
public Long getClientes(@RequestParam Long tipo) {
    Long cantidad = dashboardRepo.contarClientesPorTipo(tipo);
    return (cantidad != null) ? cantidad : 0L;}

    // Consultar cuánto ha comprado un cliente específico en total
    @GetMapping("/ventas-cliente")
    public BigDecimal getVentas(@RequestParam Long cedula) {
        return dashboardRepo.totalVentasCliente(cedula);
    }
}