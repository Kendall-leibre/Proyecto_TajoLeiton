package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TAJO_ROLES_TB", schema = "TAJO_PROYECTO")
@Data
public class Rol {
    
    @Id // Usamos ID_ROL como identificador para JPA si es único, o puedes manejarlo como entidad normal
    @Column(name = "ID_ROL")
    private Long idRol;

    @Column(name = "IDENTIFICACION")
    private Long identificacion;

    @Column(name = "NOMBRE_ROL")
    private String nombreRol;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;
}