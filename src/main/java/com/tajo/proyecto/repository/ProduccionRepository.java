package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long> {
        @Query("SELECT p FROM Produccion p " +
                        "LEFT JOIN FETCH p.material " +
                        "LEFT JOIN FETCH p.estado")
        List<Produccion> findAllOptimized();

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_PRODUCCION_INSERTAR_SP")
        void insertarProduccion(
                        @Param("P_ID_PRODUCCION") Long id,
                        @Param("P_ID_MATERIAL") Long idMat,
                        @Param("P_CANTIDAD") BigDecimal cant,
                        @Param("P_FECHA") Date fecha,
                        @Param("P_ID_ESTADO") BigDecimal estado);

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_PRODUCCION_MODIFICAR_SP")
        void modificarProduccion(
                        @Param("P_ID_PRODUCCION") Long id,
                        @Param("P_ID_MATERIAL_NUEVO") Long idMat, 
                        @Param("P_CANTIDAD_NUEVA") BigDecimal cant,
                        @Param("P_FECHA_NUEVA") java.util.Date fecha,
                        @Param("P_ID_ESTADO_NUEVO") BigDecimal estado);
                                                                        

        @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_PRODUCCION_ELIMINAR_SP")
        void eliminarProduccion(@Param("P_ID_PRODUCCION") Long id);
}