package com.devsu.bank.service.impl;

import com.devsu.bank.dto.request.MovimientoRequest;
import com.devsu.bank.dto.response.MovimientoResponse;
import com.devsu.bank.dto.response.ReporteItemResponse;
import com.devsu.bank.dto.response.ReporteResponse;
import com.devsu.bank.entity.Cuenta;
import com.devsu.bank.entity.Movimiento;
import com.devsu.bank.entity.TipoMovimiento;
import com.devsu.bank.exception.CupoDiarioExcedidoException;
import com.devsu.bank.exception.ResourceNotFoundException;
import com.devsu.bank.mapper.MovimientoMapper;
import com.devsu.bank.repository.CuentaRepository;
import com.devsu.bank.repository.MovimientoRepository;
import com.devsu.bank.service.MovimientoService;
import com.devsu.bank.service.PdfService;
import com.devsu.bank.service.strategy.MovimientoStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoMapper movimientoMapper;
    private final PdfService pdfService;

    // Inyección del mapa de strategies por nombre de bean
    private final Map<String, MovimientoStrategy> strategies;

    @Value("${negocio.limite-retiro-diario:1000}")
    private BigDecimal limiteRetiroDiario;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponse> listar() {
        return movimientoRepository.findAll()
                .stream()
                .map(movimientoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoResponse obtenerPorId(Long id) {
        return movimientoMapper.toResponse(buscarOFallar(id));
    }

    @Override
    public MovimientoResponse crear(MovimientoRequest req) {
        Cuenta cuenta = resolverCuenta(req.getCuentaId());
        LocalDate fecha = req.getFecha() != null ? req.getFecha() : LocalDate.now();

        // Validar cupo diario si es débito
        if (req.getTipoMovimiento() == TipoMovimiento.DEBITO) {
            validarCupoDiario(cuenta.getCuentaId(), req.getValor(), fecha);
        }

        // Obtener saldo actual de la cuenta
        BigDecimal saldoActual = obtenerSaldoActual(cuenta);

        // Aplicar strategy según tipo
        MovimientoStrategy strategy = strategies.get(
            req.getTipoMovimiento() == TipoMovimiento.CREDITO ? "creditoStrategy" : "debitoStrategy"
        );
        BigDecimal nuevoSaldo = strategy.calcularNuevoSaldo(saldoActual, req.getValor());

        // El valor se guarda negativo para débito, positivo para crédito
        BigDecimal valorGuardado = req.getTipoMovimiento() == TipoMovimiento.DEBITO
                ? req.getValor().abs().negate()
                : req.getValor().abs();

        Movimiento mov = new Movimiento();
        mov.setFecha(fecha);
        mov.setTipoMovimiento(req.getTipoMovimiento());
        mov.setValor(valorGuardado);
        mov.setSaldo(nuevoSaldo);
        mov.setCuenta(cuenta);

        // Actualizar saldo inicial de la cuenta
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        return movimientoMapper.toResponse(movimientoRepository.save(mov));
    }

    @Override
    public MovimientoResponse actualizar(Long id, MovimientoRequest req) {
        Movimiento mov = buscarOFallar(id);
        if (req.getFecha() != null) mov.setFecha(req.getFecha());
        if (req.getTipoMovimiento() != null) mov.setTipoMovimiento(req.getTipoMovimiento());
        if (req.getValor() != null) mov.setValor(req.getValor());
        return movimientoMapper.toResponse(movimientoRepository.save(mov));
    }

    @Override
    public MovimientoResponse actualizarParcial(Long id, MovimientoRequest req) {
        return actualizar(id, req);
    }

    @Override
    public void eliminar(Long id) {
        buscarOFallar(id);
        movimientoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteResponse generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Movimiento> movimientos = movimientoRepository.findReporte(clienteId, fechaInicio, fechaFin);

        List<ReporteItemResponse> items = movimientos.stream()
                .map(m -> ReporteItemResponse.builder()
                        .fecha(m.getFecha())
                        .cliente(m.getCuenta().getCliente().getNombre())
                        .numeroCuenta(m.getCuenta().getNumeroCuenta())
                        .tipoCuenta(m.getCuenta().getTipoCuenta().name())
                        .saldoInicial(m.getCuenta().getSaldoInicial())
                        .estado(m.getCuenta().getEstado())
                        .movimiento(m.getValor())
                        .saldoDisponible(m.getSaldo())
                        .build())
                .toList();

        byte[] pdfBytes = pdfService.generarReportePdf(items);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);

        return ReporteResponse.builder()
                .movimientos(items)
                .pdfBase64(pdfBase64)
                .build();
    }

    // Saldo actual = saldo del último movimiento o saldo inicial de la cuenta
    private BigDecimal obtenerSaldoActual(Cuenta cuenta) {
        return movimientoRepository.findLastByCuentaId(cuenta.getCuentaId())
                .map(Movimiento::getSaldo)
                .orElse(cuenta.getSaldoInicial());
    }

    private void validarCupoDiario(Long cuentaId, BigDecimal valor, LocalDate fecha) {
        BigDecimal totalHoy = movimientoRepository.sumValorByFechaAndTipo(
                cuentaId, TipoMovimiento.DEBITO, fecha);
        if (totalHoy.add(valor.abs()).compareTo(limiteRetiroDiario) > 0) {
            throw new CupoDiarioExcedidoException();
        }
    }

    private Movimiento buscarOFallar(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento", id));
    }

    private Cuenta resolverCuenta(Long cuentaId) {
        return cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta", cuentaId));
    }
}
