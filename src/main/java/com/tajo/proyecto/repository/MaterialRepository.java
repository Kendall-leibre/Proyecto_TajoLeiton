package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MATERIALES_INSERTAR_SP")
    void insertarMaterialSP(
            @Param("P_ID_MATERIAL") Long id,
            @Param("P_NOMBRE") String nombre,
            @Param("P_UNIDAD_MEDIDA") String unidad,
            @Param("P_FUNCION") String funcion,
            @Param("P_ID_ESTADO") Integer estado);

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MATERIALES_MODIFICAR_SP")
    void modificarMaterialSP(
            @Param("P_ID_MATERIAL") Long id,
            @Param("P_NOMBRE_NUEVO") String nombre,
            @Param("P_UNIDAD_NUEVA") String unidad,
            @Param("P_FUNCION_NUEVA") String funcion,
            @Param("P_ESTADO_NUEVO") Integer estado 
    );

    @Procedure(procedureName = "TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MATERIALES_ELIMINAR_SP")
    void eliminarMaterialSP(
            @Param("P_ID_MATERIAL") Long id);

    
}