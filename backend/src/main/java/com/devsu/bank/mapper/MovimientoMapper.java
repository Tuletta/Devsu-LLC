package com.devsu.bank.mapper;

import com.devsu.bank.dto.response.MovimientoResponse;
import com.devsu.bank.entity.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public MovimientoResponse toResponse(Movimiento m) {
        return MovimientoResponse.builder()
                .movimientoId(m.getMovimientoId())
                .fecha(m.getFecha())
                .tipoMovimiento(m.getTipoMovimiento())
                .valor(m.getValor())
                .saldo(m.getSaldo())
                .cuentaId(m.getCuenta().getCuentaId())
                .numeroCuenta(m.getCuenta().getNumeroCuenta())
                .build();
    }
}
