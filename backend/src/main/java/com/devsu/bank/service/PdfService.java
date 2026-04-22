package com.devsu.bank.service;

import com.devsu.bank.dto.response.ReporteItemResponse;

import java.util.List;

public interface PdfService {

    byte[] generarReportePdf(List<ReporteItemResponse> items);
}
