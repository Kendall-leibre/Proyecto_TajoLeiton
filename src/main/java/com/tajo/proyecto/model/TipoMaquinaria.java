package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@IdClass(TipoMaquinariaId.class) // Aquí se vinculan
@Table(name = "TAJO_TIPO_MAQUINARIA_TB", schema = "TAJO_PROYECTO")
public class TipoMaquinaria {

    @Id
    @Column(name = "PLACA")
    private BigDecimal placa;

    @Id
    @Column(name = "ID_TIPO_MAQUINARIA")
    private BigDecimal idTipo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ID_ESTADO")
    private BigDecimal idEstado;
}

