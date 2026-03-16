package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TAJO_MAQUINARIA_TB", schema = "TAJO_PROYECTO")
public class Maquinaria {

    @Id
    @Column(name = "PLACA")
    private BigDecimal placa; // En tu SQL es NUMBER

    @Column(name = "MODELO")
    private BigDecimal modelo; // Ojo: En tu SQL pusiste NUMBER para modelo

    @Column(name = "HORAS_TRABAJADAS")
    private BigDecimal horasTrabajadas;

    @Column(name = "ID_ESTADO")
    private BigDecimal idEstado;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", insertable = false, updatable = false)
    private Estado estado;
}