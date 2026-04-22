package com.devsu.bank.controller;

import com.devsu.bank.dto.response.ReporteResponse;
import com.devsu.bank.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Estado de cuenta por rango de fechas")
public class ReporteController {

    private final MovimientoService movimientoService;

    @GetMapping
    @Operation(summary = "Reporte JSON + PDF base64 por cliente y rango de fechas")
    public ResponseEntity<ReporteResponse> reporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(movimientoService.generarReporte(clienteId, fechaInicio, fechaFin));
    }
}
