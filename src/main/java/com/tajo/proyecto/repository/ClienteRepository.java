package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_CLIENTES_INSERTAR_SP")
    void insertarClienteSP(
        @Param("P_CEDULA") String cedula,
        @Param("P_NOMBRE_CLIENTE") String nombre,
        @Param("P_APELLIDO_PATERNO") String apePat,
        @Param("P_APELLIDO_MATERNO") String apeMat,
        @Param("P_ID_TIPO_CLIENTE") Integer idTipo,
        @Param("P_CONTRASENA") String contrasena,
        @Param("P_ID_ESTADO") Integer idEstado
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_CLIENTES_MODIFICAR_SP")
    void modificarClienteSP(
        @Param("P_CEDULA") String cedula,
        @Param("P_NOMBRE_CLIENTE_NUEVO") String nombre,
        @Param("P_APELLIDO_PATERNO_NUEVO") String apePat,
        @Param("P_APELLIDO_MATERNO_NUEVO") String apeMat,
        @Param("P_ID_TIPO_CLIENTE_NUEVO") Integer idTipo
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_CLIENTES_ELIMINAR_SP")
    void eliminarClienteSP(
        @Param("P_CEDULA") String cedula
    );

}