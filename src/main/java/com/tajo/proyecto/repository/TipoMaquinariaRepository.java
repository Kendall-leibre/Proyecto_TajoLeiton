package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.TipoMaquinaria;
import com.tajo.proyecto.model.TipoMaquinariaId; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List; 

@Repository
public interface TipoMaquinariaRepository extends JpaRepository<TipoMaquinaria, TipoMaquinariaId> {

    /**
     * Busca registros por ID_TIPO ignorando la placa.
     * Útil para recuperar la descripción (ej: "Excavadora") de un tipo existente.
     */
    List<TipoMaquinaria> findByIdTipo(BigDecimal idTipo);

    /**
     * Busca por placa únicamente (aunque sea parte de una PK compuesta).
     * Útil para el proceso de inactivación/eliminación lógica.
     */
    List<TipoMaquinaria> findByPlaca(BigDecimal placa);

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TIPO_MAQUINARIA_INSERTAR_SP")
    void insertarTipoMaquinaria(
        @Param("P_PLACA") BigDecimal placa,
        @Param("P_ID_TIPO") BigDecimal idTipo,
        @Param("P_DESCRIPCION") String descripcion,
        @Param("P_ID_ESTADO") BigDecimal estado
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TIPO_MAQUINARIA_MODIFICAR_SP")
    void modificarTipoMaquinaria(
        @Param("P_PLACA") BigDecimal placa,
        @Param("P_DESCRIPCION_NUEVA") String descripcion
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TIPO_MAQUINARIA_ELIMINAR_SP")
    void eliminarTipoMaquinaria(@Param("P_PLACA") BigDecimal placa);
}