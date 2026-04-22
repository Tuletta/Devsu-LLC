package com.devsu.bank.controller;

import com.devsu.bank.dto.request.MovimientoRequest;
import com.devsu.bank.dto.response.MovimientoResponse;
import com.devsu.bank.entity.TipoMovimiento;
import com.devsu.bank.exception.CupoDiarioExcedidoException;
import com.devsu.bank.exception.SaldoInsuficienteException;
import com.devsu.bank.service.MovimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovimientoController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@SuppressWarnings("null")
class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovimientoService movimientoService;

    @Test
    void crearMovimiento_Exitoso() throws Exception {
        MovimientoRequest req = new MovimientoRequest();
        req.setCuentaId(1L);
        req.setTipoMovimiento(TipoMovimiento.CREDITO);
        req.setValor(new BigDecimal("600"));

        MovimientoResponse res = MovimientoResponse.builder()
                .movimientoId(1L)
                .fecha(LocalDate.now())
                .tipoMovimiento(TipoMovimiento.CREDITO)
                .valor(new BigDecimal("600"))
                .saldo(new BigDecimal("700"))
                .cuentaId(1L)
                .numeroCuenta("225487")
                .build();

        when(movimientoService.crear(any(MovimientoRequest.class))).thenReturn(res);

        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saldo").value(700));
    }

    @Test
    void crearMovimiento_SaldoInsuficiente() throws Exception {
        MovimientoRequest req = new MovimientoRequest();
        req.setCuentaId(1L);
        req.setTipoMovimiento(TipoMovimiento.DEBITO);
        req.setValor(new BigDecimal("1000"));

        when(movimientoService.crear(any(MovimientoRequest.class)))
                .thenThrow(new SaldoInsuficienteException());

        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensaje").value("Saldo no disponible"));
    }

    @Test
    void crearMovimiento_CupoExcedido() throws Exception {
        MovimientoRequest req = new MovimientoRequest();
        req.setCuentaId(1L);
        req.setTipoMovimiento(TipoMovimiento.DEBITO);
        req.setValor(new BigDecimal("1200"));

        when(movimientoService.crear(any(MovimientoRequest.class)))
                .thenThrow(new CupoDiarioExcedidoException());

        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.mensaje").value("Cupo diario Excedido"));
    }
}
