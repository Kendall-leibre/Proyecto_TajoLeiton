package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TAJO_TURNOS_TB", schema = "TAJO_PROYECTO")
@Data
public class Turno {
    @Id
    @Column(name = "ID_TURNO")
    private Long idTurno;

    @Column(name = "NOMBRE_TURNO") // Según me pasaste: NOMBRE_TURNO
    private String nombreTurno;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;
}