package com.devsu.bank.service.strategy;

import java.math.BigDecimal;

// Contrato del patrón Strategy para calcular el nuevo saldo
public interface MovimientoStrategy {

    BigDecimal calcularNuevoSaldo(BigDecimal saldoActual, BigDecimal valor);
}
