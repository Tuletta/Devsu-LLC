package com.devsu.bank.dto.response;

import com.devsu.bank.entity.TipoCuenta;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CuentaResponse {

    private Long cuentaId;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private Long clienteId;
    private String clienteNombre;
}
