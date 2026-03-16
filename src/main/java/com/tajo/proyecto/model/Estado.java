package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "TAJO_ESTADOS_TB", schema = "TAJO_PROYECTO")
public class Estado {
    @Id
    @Column(name = "ID_ESTADO")
    private BigDecimal idEstado;

    @Column(name = "NOMBRE_ESTADO")
    private String nombreEstado;
}