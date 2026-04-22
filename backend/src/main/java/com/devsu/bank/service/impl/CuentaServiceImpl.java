package com.devsu.bank.service.impl;

import com.devsu.bank.dto.request.CuentaRequest;
import com.devsu.bank.dto.response.CuentaResponse;
import com.devsu.bank.entity.Cliente;
import com.devsu.bank.entity.Cuenta;
import com.devsu.bank.exception.ResourceNotFoundException;
import com.devsu.bank.mapper.CuentaMapper;
import com.devsu.bank.repository.ClienteRepository;
import com.devsu.bank.repository.CuentaRepository;
import com.devsu.bank.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaResponse> listar() {
        return cuentaRepository.findAll()
                .stream()
                .map(cuentaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaResponse obtenerPorId(Long id) {
        return cuentaMapper.toResponse(buscarOFallar(id));
    }

    @Override
    public CuentaResponse crear(CuentaRequest req) {
        Cliente cliente = resolverCliente(req.getClienteId());
        Cuenta cuenta = cuentaMapper.toEntity(req, cliente);
        return cuentaMapper.toResponse(cuentaRepository.save(cuenta));
    }

    @Override
    public CuentaResponse actualizar(Long id, CuentaRequest req) {
        Cuenta cuenta = buscarOFallar(id);
        Cliente cliente = resolverCliente(req.getClienteId());
        cuentaMapper.updateEntity(cuenta, req, cliente);
        return cuentaMapper.toResponse(cuentaRepository.save(cuenta));
    }

    @Override
    public CuentaResponse actualizarParcial(Long id, CuentaRequest req) {
        Cuenta cuenta = buscarOFallar(id);
        if (req.getNumeroCuenta() != null) cuenta.setNumeroCuenta(req.getNumeroCuenta());
        if (req.getTipoCuenta() != null)   cuenta.setTipoCuenta(req.getTipoCuenta());
        if (req.getSaldoInicial() != null) cuenta.setSaldoInicial(req.getSaldoInicial());
        if (req.getEstado() != null)       cuenta.setEstado(req.getEstado());
        if (req.getClienteId() != null)    cuenta.setCliente(resolverCliente(req.getClienteId()));
        return cuentaMapper.toResponse(cuentaRepository.save(cuenta));
    }

    @Override
    public void eliminar(Long id) {
        buscarOFallar(id);
        cuentaRepository.deleteById(id);
    }

    private Cuenta buscarOFallar(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", id));
    }

    private Cliente resolverCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", clienteId));
    }
}
