package com.devsu.bank.dto.response;

import com.devsu.bank.entity.TipoMovimiento;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class MovimientoResponse {

    private Long movimientoId;
    private LocalDate fecha;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Long cuentaId;
    private String numeroCuenta;
}
