package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Extraccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ExtraccionRepository extends JpaRepository<Extraccion, Long> {
        @Query("SELECT e FROM Extraccion e " +
                        "LEFT JOIN FETCH e.material " +
                        "LEFT JOIN FETCH e.usuario " +
                        "LEFT JOIN FETCH e.estado")
        List<Extraccion> findAllOptimized();

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_EXTRACCIONES_INSERTAR_SP")
        void insertarExtraccion(
                        @Param("P_ID_EXTRACCION") Long id,
                        @Param("P_IDENTIFICACION") Long ident,
                        @Param("P_ID_MATERIAL") Long idMat,
                        @Param("P_FECHA") Date fecha,
                        @Param("P_ID_ESTADO") BigDecimal estado);

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_EXTRACCIONES_MODIFICAR_SP")
        void modificarExtraccion(
                        @Param("P_ID_EXTRACCION") Long id,
                        @Param("P_IDENTIFICACION_NUEVA") Long ident,
                        @Param("P_ID_MATERIAL_NUEVO") Long idMat,
                        @Param("P_FECHA_NUEVA") Date fecha,
                        @Param("P_ESTADO_NUEVO") BigDecimal estado);

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_EXTRACCIONES_ELIMINAR_SP")
        void eliminarExtraccion(@Param("P_ID_EXTRACCION") Long id);
}