package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "TAJO_VENTAS_TB", schema = "TAJO_PROYECTO")
public class Venta {

    @Id
    @Column(name = "ID_VENTA")
    private Long idVenta;

    @Column(name = "CEDULA")
    private Long cedula;

    // Nota: Aunque el SP lo tiene, en un Maestro-Detalle puro el material va en el detalle.
    // Lo dejamos aquí porque tu SP TAJO_VENTAS_INSERTAR_SP lo pide, pero tené en cuenta
    // que esto podría ser redundante si manejas múltiples materiales en el detalle.
    @Column(name = "ID_MATERIAL")
    private Long idMaterial;

    @Column(name = "CANTIDAD_VENTA")
    private BigDecimal cantidadVenta;

    @Column(name = "PRECIO")
    private BigDecimal precio;

    @Column(name = "FECHA_VENTA")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVenta;

    @Column(name = "ID_ESTADO")
    private Long idEstado;
}