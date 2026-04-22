package com.devsu.bank.controller;

import com.devsu.bank.dto.request.CuentaRequest;
import com.devsu.bank.dto.response.CuentaResponse;
import com.devsu.bank.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
@Tag(name = "Cuentas", description = "CRUD de cuentas bancarias")
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    @Operation(summary = "Listar todas las cuentas")
    public ResponseEntity<List<CuentaResponse>> listar() {
        return ResponseEntity.ok(cuentaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta por ID")
    public ResponseEntity<CuentaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear cuenta")
    public ResponseEntity<CuentaResponse> crear(@Valid @RequestBody CuentaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.crear(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cuenta completo")
    public ResponseEntity<CuentaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CuentaRequest req) {
        return ResponseEntity.ok(cuentaService.actualizar(id, req));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar cuenta parcialmente")
    public ResponseEntity<CuentaResponse> actualizarParcial(
            @PathVariable Long id,
            @RequestBody CuentaRequest req) {
        return ResponseEntity.ok(cuentaService.actualizarParcial(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cuenta")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
