package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.Date;

@Repository
public interface ClienteDashboardRepository extends JpaRepository<Cliente, String> {

    @Query(value = "SELECT NVL(TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_TOTAL_CLIENTE_FN(:cedula), 0) FROM DUAL", nativeQuery = true)
    BigDecimal totalCompradoPorCliente(@Param("cedula") String cedula);

    @Query(value = "SELECT NVL(TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_ACTIVAS_CLIENTE_FN(:cedula), 0) FROM DUAL", nativeQuery = true)
    Integer ventasActivasCliente(@Param("cedula") String cedula);

    // ---> AQUÍ ESTÁ EL MÉTODO QUE FALTABA <---
    @Query(value = "SELECT NVL(MAX(ID_VENTA), 0) + 1 FROM TAJO_PROYECTO.TAJO_VENTAS_TB", nativeQuery = true)
    Long obtenerSiguienteIdVenta();

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_INSERTAR_SP")
    void registrarNuevaVenta(
        @Param("P_ID_VENTA") Long idVenta,
        @Param("P_CEDULA") Long cedula,
        @Param("P_ID_MATERIAL") Long idMaterial,
        @Param("P_CANTIDAD") Double cantidad,
        @Param("P_PRECIO") Double precio,
        @Param("P_FECHA") Date fecha,
        @Param("P_ID_ESTADO") Integer idEstado
    );
}