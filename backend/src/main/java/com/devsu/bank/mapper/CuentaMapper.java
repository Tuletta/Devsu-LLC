package com.devsu.bank.mapper;

import com.devsu.bank.dto.request.CuentaRequest;
import com.devsu.bank.dto.response.CuentaResponse;
import com.devsu.bank.entity.Cliente;
import com.devsu.bank.entity.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public Cuenta toEntity(CuentaRequest req, Cliente cliente) {
        Cuenta c = new Cuenta();
        c.setNumeroCuenta(req.getNumeroCuenta());
        c.setTipoCuenta(req.getTipoCuenta());
        c.setSaldoInicial(req.getSaldoInicial());
        c.setEstado(req.getEstado() != null ? req.getEstado() : true);
        c.setCliente(cliente);
        return c;
    }

    public CuentaResponse toResponse(Cuenta c) {
        return CuentaResponse.builder()
                .cuentaId(c.getCuentaId())
                .numeroCuenta(c.getNumeroCuenta())
                .tipoCuenta(c.getTipoCuenta())
                .saldoInicial(c.getSaldoInicial())
                .estado(c.getEstado())
                .clienteId(c.getCliente().getClienteId())
                .clienteNombre(c.getCliente().getNombre())
                .build();
    }

    public void updateEntity(Cuenta c, CuentaRequest req, Cliente cliente) {
        c.setNumeroCuenta(req.getNumeroCuenta());
        c.setTipoCuenta(req.getTipoCuenta());
        c.setSaldoInicial(req.getSaldoInicial());
        c.setCliente(cliente);
        if (req.getEstado() != null) c.setEstado(req.getEstado());
    }
}
