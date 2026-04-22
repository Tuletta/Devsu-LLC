package com.devsu.bank.exception;

// Lanzada cuando se supera el límite diario de retiros
public class CupoDiarioExcedidoException extends RuntimeException {

    public CupoDiarioExcedidoException() {
        super("Cupo diario Excedido");
    }
}
