package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Usuario;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // --- OPTIMIZACIÓN DE CARGA (Para que el Login y Dashboard vuelen) ---

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.identificacion = :id")
    Optional<Usuario> findByIdentificacionOptimized(@Param("id") Long id);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles")
    List<Usuario> findAllOptimized();

    // --- CONSULTAS EXISTENTES ---

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_USUARIOS_VALIDAR_ACCESO_FN(:id, :pass) FROM DUAL", nativeQuery = true)
    Integer validarAcceso(@Param("id") String id, @Param("pass") String pass);

    @Modifying
    @Transactional
    @Query(value = "BEGIN TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_CLIENTES_INSERTAR_SP(:cedula, :nombre, :ape1, :ape2, :tipo, :estado); END;", nativeQuery = true)
    void registrarClienteSP(
            @Param("cedula") String cedula,
            @Param("nombre") String nombre,
            @Param("ape1") String ape1,
            @Param("ape2") String ape2,
            @Param("tipo") Integer tipo,
            @Param("estado") Integer estado);

    @Query(value = "SELECT ID_ROL FROM TAJO_PROYECTO.TAJO_ROLES_TB WHERE IDENTIFICACION = :id", nativeQuery = true)
    Integer obtenerRolPorIdentificacion(@Param("id") Long id);

    // --- PROCEDIMIENTOS ALMACENADOS ---

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_USUARIOS_INSERTAR_SP")
    void insertarUsuario(
        @Param("P_IDENTIFICACION") Long identificacion,
        @Param("P_NOMBRE") String nombre,
        @Param("P_APELLIDO_PATERNO") String ape1,
        @Param("P_APELLIDO_MATERNO") String ape2,
        @Param("P_EDAD") Integer edad,
        @Param("P_FECHA_INGRESO") Date fecha,
        @Param("P_ID_TURNO") BigDecimal turno,
        @Param("P_PLACA") BigDecimal placa,
        @Param("P_ID_ESTADO") BigDecimal estado,
        @Param("P_CONTRASENA") String contrasena
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_USUARIOS_MODIFICAR_SP")
    void modificarUsuario(
        @Param("P_IDENTIFICACION") Long identificacion,
        @Param("P_NOMBRE") String nombre,
        @Param("P_APE_PATERNO") String ape1,
        @Param("P_APE_MATERNO") String ape2,
        @Param("P_EDAD") Integer edad,
        @Param("P_ID_TURNO_NUEVO") BigDecimal turno,
        @Param("P_PLACA_NUEVA") BigDecimal placa,
        @Param("P_ID_ESTADO_NUEVO") BigDecimal estado
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_USUARIOS_ELIMINAR_SP")
    void eliminarUsuario(@Param("P_IDENTIFICACION") Long identificacion);
}