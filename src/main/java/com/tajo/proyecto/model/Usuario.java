package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TAJO_USUARIOS_TB", schema = "TAJO_PROYECTO")
@Data
public class Usuario {

    @Id
    @Column(name = "IDENTIFICACION")
    private Long identificacion;

    @Column(name = "NOMBRE_USUARIO")
    private String nombre;

    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @Column(name = "EDAD")
    private Integer edad;

    @Column(name = "FECHA_INGRESO")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaIngreso;

    @Column(name = "ID_TURNO")
    private BigDecimal idTurno;

    @Column(name = "PLACA")
    private BigDecimal placa;

    @Column(name = "ID_ESTADO")
    private BigDecimal idEstado;

    @Column(name = "CONTRASENA")
    private String contrasena;

    // En Usuario.java
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDENTIFICACION", referencedColumnName = "IDENTIFICACION", insertable = false, updatable = false)
    private List<Rol> roles; // Ahora es una lista
}