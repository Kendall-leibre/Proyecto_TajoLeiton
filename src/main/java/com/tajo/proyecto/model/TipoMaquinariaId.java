package com.tajo.proyecto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMaquinariaId implements Serializable {
    private BigDecimal placa;
    private BigDecimal idTipo;
}