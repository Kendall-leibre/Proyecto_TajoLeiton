package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_INSERTAR_SP")
    void insertarVenta(
            @Param("P_ID_VENTA") Long idVenta,
            @Param("P_CEDULA") Long cedula,
            @Param("P_ID_MATERIAL") Long idMaterial,
            @Param("P_CANTIDAD") BigDecimal cantidad,
            @Param("P_PRECIO") BigDecimal precio,
            @Param("P_FECHA") Date fecha,
            @Param("P_ID_ESTADO") BigDecimal estado
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_MODIFICAR_SP")
    void modificarVenta(
            @Param("P_ID_VENTA") Long idVenta,
            @Param("P_ID_MATERIAL_NUEVO") Long idMaterialNuevo,
            @Param("P_CANTIDAD_NUEVA") BigDecimal cantidadNueva,
            @Param("P_PRECIO_NUEVO") BigDecimal precioNuevo
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_ELIMINAR_SP")
    void eliminarVenta(
            @Param("P_ID_VENTA") Long idVenta
    );
}