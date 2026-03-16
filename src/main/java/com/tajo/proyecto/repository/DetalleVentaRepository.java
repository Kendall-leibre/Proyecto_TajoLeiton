package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.DetalleVenta;
import com.tajo.proyecto.model.DetalleVentaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, DetalleVentaId> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_DETALLE_VENTAS_INSERTAR_SP")
    void insertarDetalleVenta(
            @Param("P_ID_VENTA") Long idVenta,
            @Param("P_ID_MATERIAL") Long idMaterial,
            @Param("P_PRECIO_TOTAL") BigDecimal precioTotal,
            @Param("P_ID_ESTADO") BigDecimal estado
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_DETALLE_VENTAS_MODIFICAR_SP")
    void modificarDetalleVenta(
            @Param("P_ID_VENTA") Long idVenta,
            @Param("P_ID_MATERIAL") Long idMaterial,
            @Param("P_PRECIO_NUEVO") BigDecimal precioNuevo
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_DETALLE_VENTAS_ELIMINAR_SP")
    void eliminarDetalleVenta(
            @Param("P_ID_VENTA") Long idVenta,
            @Param("P_ID_MATERIAL") Long idMaterial
    );
}