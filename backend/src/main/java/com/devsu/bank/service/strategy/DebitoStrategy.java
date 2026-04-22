package com.devsu.bank.service.strategy;

import com.devsu.bank.exception.SaldoInsuficienteException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// Estrategia débito: resta el valor y valida saldo suficiente
@Component("debitoStrategy")
public class DebitoStrategy implements MovimientoStrategy {

    @Override
    public BigDecimal calcularNuevoSaldo(BigDecimal saldoActual, BigDecimal valor) {
        if (saldoActual.compareTo(BigDecimal.ZERO) == 0) {
            throw new SaldoInsuficienteException();
        }
        BigDecimal nuevo = saldoActual.subtract(valor.abs());
        if (nuevo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException();
        }
        return nuevo;
    }
}
