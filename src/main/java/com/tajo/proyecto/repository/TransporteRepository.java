package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {
        @Query("SELECT t FROM Transporte t " +
                        "LEFT JOIN FETCH t.material " +
                        "LEFT JOIN FETCH t.usuario " +
                        "LEFT JOIN FETCH t.estado")
        List<Transporte> findAllOptimized();

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TRANSPORTE_INSERTAR_SP")
        void insertarTransporte(
                        @Param("P_ID_TRANSPORTE") Long idTransporte,
                        @Param("P_PLACA") Long placa,
                        @Param("P_IDENTIFICACION") Long identificacion,
                        @Param("P_ID_MATERIAL") Long idMaterial,
                        @Param("P_CANTIDAD_TRANSPORTADA") BigDecimal cantidad,
                        @Param("P_PRECIO_TRANSPORTE") BigDecimal precio,
                        @Param("P_DESTINO") String destino,
                        @Param("P_ID_ESTADO") BigDecimal estado);

        // Solo los 6 parámetros que pusiste en tu SP de Oracle
        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TRANSPORTE_MODIFICAR_SP")
        void modificarTransporte(
                        @Param("P_ID_TRANSPORTE") Long idTransporte,
                        @Param("P_PLACA_NUEVA") Long placa,
                        @Param("P_IDENTIFICACION_NUEVA") Long identificacion,
                        @Param("P_CANTIDAD_NUEVA") BigDecimal cantidad,
                        @Param("P_PRECIO_NUEVO") BigDecimal precio,
                        @Param("P_DESTINO_NUEVO") String destino);

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TRANSPORTE_ELIMINAR_SP")
        void eliminarTransporte(
                        @Param("P_ID_TRANSPORTE") Long idTransporte);
}