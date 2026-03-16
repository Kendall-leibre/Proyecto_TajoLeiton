package com.tajo.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TAJO_PRODUCCION_TB", schema = "TAJO_PROYECTO")
@Data // Esto genera los Getters y Setters automáticamente
public class Produccion {

    @Id
    @Column(name = "ID_PRODUCCION") // Verifica que este nombre sea EXACTO al de la tabla
    private Long idProduccion;

    @Column(name = "ID_MATERIAL")
    private Long idMaterial;

    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;

    @Column(name = "FECHA_PRODUCCION")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE) // Esto ayuda a Oracle con el formato de fecha
    private Date fechaProduccion;

    @Column(name = "ID_ESTADO") // Aquí es donde se perdía el valor del estado
    private Integer idEstado; // Usamos Integer o Long para que coincida con el número en la DB

    // RELACIÓN CON MATERIAL
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para que traiga el nombre de una vez
    @JoinColumn(name = "ID_MATERIAL", referencedColumnName = "ID_MATERIAL", insertable = false, updatable = false)
    private Material material;

    // Dentro de tus clases Produccion y Extraccion
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO", insertable = false, updatable = false)
    private Estado estado;
}