package com.devsu.bank.service;

import com.devsu.bank.dto.request.MovimientoRequest;
import com.devsu.bank.dto.response.MovimientoResponse;
import com.devsu.bank.dto.response.ReporteResponse;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoService {

    List<MovimientoResponse> listar();

    MovimientoResponse obtenerPorId(Long id);

    MovimientoResponse crear(MovimientoRequest req);

    MovimientoResponse actualizar(Long id, MovimientoRequest req);

    MovimientoResponse actualizarParcial(Long id, MovimientoRequest req);

    void eliminar(Long id);

    ReporteResponse generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}
