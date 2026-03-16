package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TAJO_DETALLE_VENTAS_TB", schema = "TAJO_PROYECTO")
@IdClass(DetalleVentaId.class) // Se necesita una clase para la PK compuesta (ID_VENTA + ID_MATERIAL)
public class DetalleVenta {

    @Id
    @Column(name = "ID_VENTA")
    private Long idVenta;

    @Id
    @Column(name = "ID_MATERIAL")
    private Long idMaterial;

    @Column(name = "PRECIO_TOTAL")
    private BigDecimal precioTotal;

    @Column(name = "ID_ESTADO")
    private Long idEstado;
}