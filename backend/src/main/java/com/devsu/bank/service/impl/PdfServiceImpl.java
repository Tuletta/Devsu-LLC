package com.devsu.bank.service.impl;

import com.devsu.bank.dto.response.ReporteItemResponse;
import com.devsu.bank.service.PdfService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@Slf4j
public class PdfServiceImpl implements PdfService {

    private static final String[] HEADERS = {
        "Fecha", "Cliente", "Nro. Cuenta", "Tipo", "Saldo Inicial", "Estado", "Movimiento", "Saldo Disponible"
    };

    @Override
    public byte[] generarReportePdf(List<ReporteItemResponse> items) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            doc.add(new Paragraph("Estado de Cuenta — Devsu Bank")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            Table tabla = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();

            // Encabezados
            for (String h : HEADERS) {
                tabla.addHeaderCell(new Cell()
                        .add(new Paragraph(h).setBold().setFontSize(9))
                        .setBackgroundColor(ColorConstants.DARK_GRAY)
                        .setFontColor(ColorConstants.WHITE));
            }

            // Filas
            items.forEach(item -> {
                tabla.addCell(cell(item.getFecha().toString()));
                tabla.addCell(cell(item.getCliente()));
                tabla.addCell(cell(item.getNumeroCuenta()));
                tabla.addCell(cell(item.getTipoCuenta()));
                tabla.addCell(cell(item.getSaldoInicial().toPlainString()));
                tabla.addCell(cell(item.getEstado() ? "Activa" : "Inactiva"));
                tabla.addCell(cell(item.getMovimiento().toPlainString()));
                tabla.addCell(cell(item.getSaldoDisponible().toPlainString()));
            });

            doc.add(tabla);
            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Error generando PDF", e);
            return new byte[0];
        }
    }

    private Cell cell(String texto) {
        return new Cell().add(new Paragraph(texto).setFontSize(8));
    }
}
