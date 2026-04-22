package com.devsu.bank.controller;

import com.devsu.bank.dto.request.ClienteRequest;
import com.devsu.bank.dto.response.ClienteResponse;
import com.devsu.bank.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "CRUD de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public ResponseEntity<ClienteResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear cliente")
    public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crear(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente completo")
    public ResponseEntity<ClienteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest req) {
        return ResponseEntity.ok(clienteService.actualizar(id, req));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar cliente parcialmente")
    public ResponseEntity<ClienteResponse> actualizarParcial(
            @PathVariable Long id,
            @RequestBody ClienteRequest req) {
        return ResponseEntity.ok(clienteService.actualizarParcial(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
