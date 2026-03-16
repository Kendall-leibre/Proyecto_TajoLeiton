package com.tajo.proyecto.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class DetalleVentaId implements Serializable {
    private Long idVenta;
    private Long idMaterial;
}