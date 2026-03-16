package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "TAJO_FACTURACION_TB", schema = "TAJO_PROYECTO")
public class Factura {

    @Id
    @Column(name = "ID_FACTURA")
    private Long idFactura;

    @Column(name = "CEDULA")
    private Long cedula;

    @Column(name = "ID_VENTA")
    private Long idVenta;

    @Column(name = "ID_IMPUESTO")
    private Long idImpuesto;

    @Column(name = "ID_DESCUENTO")
    private Long idDescuento;

    @Column(name = "ID_METODO_PAGO")
    private Long idMetodoPago;

    @Column(name = "FECHA_EMISION")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaEmision;

    @Column(name = "MONTO_TOTAL")
    private BigDecimal montoTotal;

    @Column(name = "ID_ESTADO")
    private Long idEstado;
}