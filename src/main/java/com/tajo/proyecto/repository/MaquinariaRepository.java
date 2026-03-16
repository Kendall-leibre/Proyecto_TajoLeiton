package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Maquinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface MaquinariaRepository extends JpaRepository<Maquinaria, BigDecimal> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MAQUINARIA_INSERTAR_SP")
    void insertarMaquinaria(
            @Param("P_PLACA") BigDecimal placa,
            @Param("P_MODELO") BigDecimal modelo,
            @Param("P_HORAS_TRABAJADAS") BigDecimal horas,
            @Param("P_ID_ESTADO") BigDecimal estado);

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MAQUINARIA_MODIFICAR_SP")
    void modificarMaquinaria(
            @Param("P_PLACA") BigDecimal placa,
            @Param("P_MODELO_NUEVO") BigDecimal modelo,
            @Param("P_HORAS_NUEVAS") BigDecimal horas,
            @Param("P_ESTADO_NUEVO") BigDecimal estado 
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MAQUINARIA_ELIMINAR_SP")
    void eliminarMaquinaria(@Param("P_PLACA") BigDecimal placa);
}