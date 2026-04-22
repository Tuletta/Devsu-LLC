package com.devsu.bank.dto.request;

import com.devsu.bank.entity.TipoCuenta;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaRequest {

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String numeroCuenta;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private BigDecimal saldoInicial;

    private Boolean estado = true;

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;
}
