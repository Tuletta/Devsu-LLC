package com.devsu.bank.controller;

import com.devsu.bank.dto.request.ClienteRequest;
import com.devsu.bank.dto.response.ClienteResponse;
import com.devsu.bank.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClienteController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@SuppressWarnings("null")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    void crearCliente_Exitoso() throws Exception {
        // Arrange
        ClienteRequest req = new ClienteRequest();
        req.setNombre("Jose Lema");
        req.setGenero("Masculino");
        req.setEdad(30);
        req.setIdentificacion("1234567890");
        req.setDireccion("Otavalo sn y principal");
        req.setTelefono("098254785");
        req.setContrasena("1234");
        req.setEstado(true);

        ClienteResponse res = ClienteResponse.builder()
                .clienteId(1L)
                .nombre("Jose Lema")
                .genero("Masculino")
                .edad(30)
                .identificacion("1234567890")
                .direccion("Otavalo sn y principal")
                .telefono("098254785")
                .estado(true)
                .build();

        when(clienteService.crear(any(ClienteRequest.class))).thenReturn(res);

        // Act & Assert
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.nombre").value("Jose Lema"));
    }
}
