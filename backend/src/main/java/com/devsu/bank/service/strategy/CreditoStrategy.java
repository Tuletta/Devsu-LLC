package com.devsu.bank.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// Estrategia crédito: suma el valor al saldo
@Component("creditoStrategy")
public class CreditoStrategy implements MovimientoStrategy {

    @Override
    public BigDecimal calcularNuevoSaldo(BigDecimal saldoActual, BigDecimal valor) {
        return saldoActual.add(valor.abs());
    }
}
