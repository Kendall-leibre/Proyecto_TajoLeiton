package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "TAJO_INVENTARIO_TB", schema = "TAJO_PROYECTO")
public class Inventario {

    @Id
    @Column(name = "ID_INVENTARIO")
    private java.math.BigDecimal idInventario; // Columna 1

    @Column(name = "ID_MATERIAL")
    private java.math.BigDecimal idMaterial; // Columna 2

    @Column(name = "CANTIDAD_MATERIAL")
    private java.math.BigDecimal cantidadMaterial; // Columna 3

    @Column(name = "NIVEL_STOCK")
    private java.math.BigDecimal nivelStock; // Columna 4

    @Column(name = "ID_TIPO_MOVIMIENTO_INVENTARIO")
    private java.math.BigDecimal idTipoMovimiento; // Columna 5

    // ESTA ES LA COLUMNA 6 QUE FALLA (ID_ESTADO)
    @Column(name = "ID_ESTADO")
    private java.math.BigDecimal idEstado; 
}