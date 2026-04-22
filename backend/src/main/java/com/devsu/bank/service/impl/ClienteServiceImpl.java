package com.devsu.bank.service.impl;

import com.devsu.bank.dto.request.ClienteRequest;
import com.devsu.bank.dto.response.ClienteResponse;
import com.devsu.bank.entity.Cliente;
import com.devsu.bank.exception.ResourceNotFoundException;
import com.devsu.bank.mapper.ClienteMapper;
import com.devsu.bank.repository.ClienteRepository;
import com.devsu.bank.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorId(Long id) {
        return clienteMapper.toResponse(buscarOFallar(id));
    }

    @Override
    public ClienteResponse crear(ClienteRequest req) {
        Cliente cliente = clienteMapper.toEntity(req);
        cliente.setContrasena(passwordEncoder.encode(req.getContrasena()));
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponse actualizar(Long id, ClienteRequest req) {
        Cliente cliente = buscarOFallar(id);
        clienteMapper.updateEntity(cliente, req);
        if (req.getContrasena() != null && !req.getContrasena().isBlank()) {
            cliente.setContrasena(passwordEncoder.encode(req.getContrasena()));
        }
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponse actualizarParcial(Long id, ClienteRequest req) {
        // PATCH: solo actualiza campos no nulos del request
        Cliente cliente = buscarOFallar(id);
        if (req.getNombre() != null)       cliente.setNombre(req.getNombre());
        if (req.getGenero() != null)       cliente.setGenero(req.getGenero());
        if (req.getEdad() != null)         cliente.setEdad(req.getEdad());
        if (req.getIdentificacion() != null) cliente.setIdentificacion(req.getIdentificacion());
        if (req.getDireccion() != null)    cliente.setDireccion(req.getDireccion());
        if (req.getTelefono() != null)     cliente.setTelefono(req.getTelefono());
        if (req.getEstado() != null)       cliente.setEstado(req.getEstado());
        if (req.getContrasena() != null && !req.getContrasena().isBlank()) {
            cliente.setContrasena(passwordEncoder.encode(req.getContrasena()));
        }
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        buscarOFallar(id);
        clienteRepository.deleteById(id);
    }

    private Cliente buscarOFallar(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
    }
}
