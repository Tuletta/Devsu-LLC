package com.devsu.bank.exception;

// Lanzada cuando no se encuentra un recurso por ID
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entidad, Object id) {
        super(String.format("%s no encontrado con id: %s", entidad, id));
    }
}
