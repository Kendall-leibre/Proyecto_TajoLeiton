package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TAJO_CONTROL_TRANSPORTE_TB", schema = "TAJO_PROYECTO")
public class Transporte {

    @Id
    @Column(name = "ID_TRANSPORTE")
    private Long idTransporte;

    @Column(name = "PLACA")
    private Long placa;

    @Column(name = "IDENTIFICACION")
    private Long identificacion;

    @Column(name = "ID_MATERIAL")
    private Long idMaterial;

    @Column(name = "CANTIDAD_TRANSPORTADA")
    private BigDecimal cantidadTransportada;

    @Column(name = "PRECIO_TRANSPORTE")
    private BigDecimal precioTransporte;

    @Column(name = "DESTINO")
    private String destino;

    @Column(name = "ID_ESTADO")
    private Long idEstado;
    
    // Relaciones opcionales (si ocupas mostrar nombres en la tabla)
    @ManyToOne
    @JoinColumn(name = "IDENTIFICACION", referencedColumnName = "IDENTIFICACION", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_MATERIAL", referencedColumnName = "ID_MATERIAL", insertable = false, updatable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", insertable = false, updatable = false)
    private Estado estado;
}