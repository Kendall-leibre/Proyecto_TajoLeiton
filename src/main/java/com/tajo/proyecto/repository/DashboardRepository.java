package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface DashboardRepository extends JpaRepository<Usuario, Long> {

    // --- CONSULTAS NATIVAS PARA LLENAR SELECTS (Listas desplegables) ---

    @Query(value = "SELECT ID_TIPO_CLIENTE, NOMBRE_TIPO FROM TAJO_PROYECTO.TAJO_TIPOS_CLIENTE_TB", nativeQuery = true)
    List<Map<String, Object>> obtenerTiposClientes();

    @Query(value = "SELECT ID_MATERIAL, NOMBRE FROM TAJO_PROYECTO.TAJO_MATERIALES_TB", nativeQuery = true)
    List<Map<String, Object>> obtenerMaterialesSimple();

    // --- FUNCIONES CON PARÁMETROS (Buscadores interactivos) ---

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_USUARIOS_OBTENER_NOMBRE_COMPLETO_FN(:cedula) FROM DUAL", nativeQuery = true)
    String obtenerNombrePorIdentificacion(@Param("cedula") Long cedula);

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_CLIENTES_CONTAR_POR_TIPO_FN(:tipo) FROM DUAL", nativeQuery = true)
    Long contarClientesPorTipo(@Param("tipo") Long tipo);

    // Stock actual (Usamos NVL para que si no hay stock devuelva 0 en lugar de
    // error)
    @Query(value = "SELECT NVL(TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MATERIAL_STOCK_ACTUAL_FN(:idMaterial), 0) FROM DUAL", nativeQuery = true)
    BigDecimal stockActualMaterial(@Param("idMaterial") Long idMaterial);

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_TURNOS_USUARIOS_CONTAR_FN(:idTurno) FROM DUAL", nativeQuery = true)
    Long contarUsuariosTurno(@Param("idTurno") Long idTurno);

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_VENTAS_TOTAL_CLIENTE_FN(:cedula) FROM DUAL", nativeQuery = true)
    BigDecimal totalVentasCliente(@Param("cedula") Long cedula);

    // --- FUNCIONES GENERALES (KPIs automáticos al cargar) ---

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MAQUINARIA_ACTIVA_CONTAR_FN() FROM DUAL", nativeQuery = true)
    Long contarMaquinariaActiva();

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_PRODUCCION_FECHA_ESPECIFICA_FN(SYSDATE) FROM DUAL", nativeQuery = true)
    BigDecimal produccionHoy();

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_MAQUINARIA_HORAS_TOTALES_FN() FROM DUAL", nativeQuery = true)
    Long contarHorasMaquinaria();

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_PLANILLAS_TOTAL_PAGADO_FN() FROM DUAL", nativeQuery = true)
    BigDecimal totalPlanillas();

    @Query(value = "SELECT TAJO_PROYECTO.TAJO_PROYECTO_FINAL_PKG.TAJO_DEDUCCIONES_PORCENTAJE_TOTAL_FN() FROM DUAL", nativeQuery = true)
    BigDecimal totalDeducciones();
}