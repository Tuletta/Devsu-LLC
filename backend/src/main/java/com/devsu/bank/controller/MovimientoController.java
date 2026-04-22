package com.devsu.bank.controller;

import com.devsu.bank.dto.request.MovimientoRequest;
import com.devsu.bank.dto.response.MovimientoResponse;
import com.devsu.bank.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "CRUD de movimientos bancarios")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public ResponseEntity<List<MovimientoResponse>> listar() {
        return ResponseEntity.ok(movimientoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento por ID")
    public ResponseEntity<MovimientoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear movimiento (aplica reglas de negocio)")
    public ResponseEntity<MovimientoResponse> crear(@Valid @RequestBody MovimientoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.crear(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar movimiento completo")
    public ResponseEntity<MovimientoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody MovimientoRequest req) {
        return ResponseEntity.ok(movimientoService.actualizar(id, req));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar movimiento parcialmente")
    public ResponseEntity<MovimientoResponse> actualizarParcial(
            @PathVariable Long id,
            @RequestBody MovimientoRequest req) {
        return ResponseEntity.ok(movimientoService.actualizarParcial(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar movimiento")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
