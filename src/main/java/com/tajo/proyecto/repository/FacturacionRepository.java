package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface FacturacionRepository extends JpaRepository<Factura, Long> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_FACTURACION_INSERTAR_SP")
    void insertarFactura(
            @Param("P_ID_FACTURA") Long idFactura,
            @Param("P_CEDULA") Long cedula,
            @Param("P_ID_VENTA") Long idVenta,
            @Param("P_ID_IMPUESTO") Long idImpuesto,
            @Param("P_ID_DESCUENTO") Long idDescuento,
            @Param("P_ID_METODO_PAGO") Long idMetodoPago,
            @Param("P_FECHA_EMISION") Date fechaEmision,
            @Param("P_MONTO_TOTAL") BigDecimal montoTotal,
            @Param("P_ID_ESTADO") BigDecimal estado
    );

    
   @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_FACTURACION_MODIFICAR_SP")
    void modificarFactura(
            @Param("P_ID_FACTURA") Long idFactura,
            @Param("P_ID_METODO_PAGO_NUEVO") Long idMetodoPagoNuevo,
            @Param("P_MONTO_NUEVO") BigDecimal montoNuevo,
            @Param("P_ID_ESTADO_NUEVO") BigDecimal estadoNuevo 
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_FACTURACION_ELIMINAR_SP")
    void eliminarFactura(
            @Param("P_ID_FACTURA") Long idFactura
    );
}