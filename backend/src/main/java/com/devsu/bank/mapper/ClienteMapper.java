package com.devsu.bank.mapper;

import com.devsu.bank.dto.request.ClienteRequest;
import com.devsu.bank.dto.response.ClienteResponse;
import com.devsu.bank.entity.Cliente;
import org.springframework.stereotype.Component;

// Conversión bidireccional entre Cliente entity y DTOs
@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest req) {
        Cliente c = new Cliente();
        c.setNombre(req.getNombre());
        c.setGenero(req.getGenero());
        c.setEdad(req.getEdad());
        c.setIdentificacion(req.getIdentificacion());
        c.setDireccion(req.getDireccion());
        c.setTelefono(req.getTelefono());
        c.setEstado(req.getEstado() != null ? req.getEstado() : true);
        return c;
    }

    public ClienteResponse toResponse(Cliente c) {
        return ClienteResponse.builder()
                .clienteId(c.getClienteId())
                .nombre(c.getNombre())
                .genero(c.getGenero())
                .edad(c.getEdad())
                .identificacion(c.getIdentificacion())
                .direccion(c.getDireccion())
                .telefono(c.getTelefono())
                .estado(c.getEstado())
                .build();
    }

    public void updateEntity(Cliente c, ClienteRequest req) {
        c.setNombre(req.getNombre());
        c.setGenero(req.getGenero());
        c.setEdad(req.getEdad());
        c.setIdentificacion(req.getIdentificacion());
        c.setDireccion(req.getDireccion());
        c.setTelefono(req.getTelefono());
        if (req.getEstado() != null) c.setEstado(req.getEstado());
    }
}
