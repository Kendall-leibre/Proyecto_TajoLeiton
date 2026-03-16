package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Inventario;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, BigDecimal> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_INVENTARIO_INSERTAR_SP")
void insertarInventarioSP(
    @Param("P_ID_INVENTARIO") BigDecimal idInv,
    @Param("P_ID_MATERIAL") BigDecimal idMat,
    @Param("P_CANTIDAD") BigDecimal cantidad,
    @Param("P_NIVEL_STOCK") BigDecimal stock,
    @Param("P_ID_TIPO_MOV") BigDecimal tipoMov,
    @Param("P_ID_ESTADO") BigDecimal estado
);

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_INVENTARIO_MODIFICAR_SP")
    void modificarInventarioSP(
        @Param("P_ID_INVENTARIO") BigDecimal idInv,
        @Param("P_CANTIDAD_NUEVA") BigDecimal cantidad,
        @Param("P_STOCK_NUEVO") BigDecimal stock
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_INVENTARIO_ELIMINAR_SP")
    void eliminarInventarioSP(
        @Param("P_ID_INVENTARIO") Long idInv,
        @Param("P_ID_MATERIAL") Long idMat
    );
}