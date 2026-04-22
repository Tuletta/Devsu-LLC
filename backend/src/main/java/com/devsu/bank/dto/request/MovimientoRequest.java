package com.devsu.bank.dto.request;

import com.devsu.bank.entity.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovimientoRequest {

    private LocalDate fecha;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimiento tipoMovimiento;

    @NotNull(message = "El valor es obligatorio")
    private BigDecimal valor;

    @NotNull(message = "La cuenta es obligatoria")
    private Long cuentaId;
}
