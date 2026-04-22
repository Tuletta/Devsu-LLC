package com.devsu.bank.service;

import com.devsu.bank.dto.request.CuentaRequest;
import com.devsu.bank.dto.response.CuentaResponse;

import java.util.List;

public interface CuentaService {

    List<CuentaResponse> listar();

    CuentaResponse obtenerPorId(Long id);

    CuentaResponse crear(CuentaRequest req);

    CuentaResponse actualizar(Long id, CuentaRequest req);

    CuentaResponse actualizarParcial(Long id, CuentaRequest req);

    void eliminar(Long id);
}
