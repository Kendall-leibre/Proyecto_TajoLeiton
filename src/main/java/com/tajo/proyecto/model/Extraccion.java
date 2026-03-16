package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TAJO_EXTRACCIONES_TB", schema = "TAJO_PROYECTO")
@Data
public class Extraccion {

    @Id
    @Column(name = "ID_EXTRACCION")
    private Long idExtraccion;

    @Column(name = "IDENTIFICACION")
    private Long identificacion;

    @Column(name = "ID_MATERIAL")
    private Long idMaterial;

    @Column(name = "FECHA_EXTRACCION")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaExtraccion;

    @Column(name = "ID_ESTADO")
    private BigDecimal idEstado;

    // Relación con Usuario (quién extrae)
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