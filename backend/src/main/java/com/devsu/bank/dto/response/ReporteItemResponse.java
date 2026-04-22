package com.devsu.bank.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

// Fila del estado de cuenta por movimiento (reporte)
@Getter
@Builder
public class ReporteItemResponse {

    private LocalDate fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}
