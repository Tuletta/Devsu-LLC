package com.devsu.bank.repository;

import com.devsu.bank.entity.Movimiento;
import com.devsu.bank.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaCuentaId(Long cuentaId);

    // Obtener el último movimiento para conocer el saldo actual sin cargar todo
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.cuentaId = :cuentaId ORDER BY m.movimientoId DESC LIMIT 1")
    java.util.Optional<Movimiento> findLastByCuentaId(@Param("cuentaId") Long cuentaId);

    // Total retirado hoy en una cuenta específica
    @Query("""
        SELECT COALESCE(SUM(ABS(m.valor)), 0)
        FROM Movimiento m
        WHERE m.cuenta.cuentaId = :cuentaId
          AND m.tipoMovimiento = :tipo
          AND m.fecha = :fecha
    """)
    BigDecimal sumValorByFechaAndTipo(
        @Param("cuentaId") Long cuentaId,
        @Param("tipo") TipoMovimiento tipo,
        @Param("fecha") LocalDate fecha
    );

    // Movimientos por cliente en rango de fechas (para reporte)
    @Query("""
        SELECT m FROM Movimiento m
        JOIN m.cuenta c
        JOIN c.cliente cl
        WHERE cl.clienteId = :clienteId
          AND m.fecha BETWEEN :inicio AND :fin
        ORDER BY m.fecha DESC
    """)
    List<Movimiento> findReporte(
        @Param("clienteId") Long clienteId,
        @Param("inicio") LocalDate inicio,
        @Param("fin") LocalDate fin
    );
}
