package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TAJO_CLIENTES_TB", schema = "TAJO_PROYECTO")
@Data
public class Cliente {

    @Id
    @Column(name = "CEDULA")
    private String cedula;

    @Column(name = "NOMBRE_CLIENTE")
    private String nombre;

    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @Column(name = "ID_TIPO_CLIENTE")
    private Integer idTipoCliente;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;

    @Column(name = "CONTRASENA")
    private String contrasena;
}