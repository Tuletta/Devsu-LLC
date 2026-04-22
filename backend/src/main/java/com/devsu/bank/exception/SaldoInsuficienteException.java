package com.devsu.bank.exception;

// Lanzada cuando el saldo de la cuenta es insuficiente para un débito
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Saldo no disponible");
    }
}
