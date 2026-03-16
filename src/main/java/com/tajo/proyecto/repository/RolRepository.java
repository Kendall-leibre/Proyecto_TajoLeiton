package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Rol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query("SELECT DISTINCT r.nombreRol FROM Rol r")
    List<String> findDistinctNombreRol();
    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_ROLES_INSERTAR_SP")
    void insertarRol(
        @Param("P_IDENTIFICACION") Long identificacion,
        @Param("P_ID_ROL") Long idRol,
        @Param("P_NOMBRE_ROL") String nombreRol,
        @Param("P_ID_ESTADO") Integer idEstado
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_ROLES_MODIFICAR_SP")
    void modificarRol(
        @Param("P_IDENTIFICACION") Long identificacion,
        @Param("P_NOMBRE_ROL_NUEVO") String nombreRolNuevo
    );
}