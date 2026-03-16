package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TAJO_MATERIALES_TB", schema = "TAJO_PROYECTO")
@Data
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Por si es autoincrementable en Oracle
    @Column(name = "ID_MATERIAL")
    private Long idMaterial;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;

    @Column(name = "FUNCION")
    private String funcion;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;
}