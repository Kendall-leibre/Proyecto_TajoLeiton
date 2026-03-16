package com.tajo.proyecto.model;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "TAJO_DESCUENTOS_TB", schema = "TAJO_PROYECTO")
public class Descuento {
    @Id
    @Column(name = "ID_DESCUENTO")
    private Long idDescuento;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    @Column(name = "ID_ESTADO")
    private Long idEstado;
}