package com.devsu.bank.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// Respuesta completa del reporte con opción de PDF en base64
@Getter
@Builder
public class ReporteResponse {

    private List<ReporteItemResponse> movimientos;
    private String pdfBase64;
}
