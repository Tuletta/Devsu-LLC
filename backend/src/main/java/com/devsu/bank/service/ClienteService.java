package com.devsu.bank.service;

import com.devsu.bank.dto.request.ClienteRequest;
import com.devsu.bank.dto.response.ClienteResponse;

import java.util.List;

public interface ClienteService {

    List<ClienteResponse> listar();

    ClienteResponse obtenerPorId(Long id);

    ClienteResponse crear(ClienteRequest req);

    ClienteResponse actualizar(Long id, ClienteRequest req);

    ClienteResponse actualizarParcial(Long id, ClienteRequest req);

    void eliminar(Long id);
}
