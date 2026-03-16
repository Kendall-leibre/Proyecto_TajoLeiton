package com.tajo.proyecto.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TAJO_METODO_PAGO_TB", schema = "TAJO_PROYECTO")
public class MetodoPago {
    @Id
    @Column(name = "ID_METODO_PAGO")
    private Long idMetodoPago;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ID_ESTADO")
    private Long idEstado;
}