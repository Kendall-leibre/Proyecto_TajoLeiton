package com.tajo.proyecto.repository;

import com.tajo.proyecto.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, BigDecimal> {
}