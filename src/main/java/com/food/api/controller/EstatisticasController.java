package com.food.api.controller;

import com.food.api.model.dto.VendaDiaria;
import com.food.domain.filter.VendaDiariaFilter;
import com.food.service.VendaQueryService;
import com.food.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;
    private final VendaReportService vendaReportService;

    @Autowired
    public EstatisticasController(VendaQueryService vendaQueryService, VendaReportService vendaReportService) {
        this.vendaQueryService = vendaQueryService;
        this.vendaReportService = vendaReportService;
    }

    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        return vendaQueryService.consultarVendasDiarias(filter);
    }

    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf")
                .body(vendaReportService.consultarVendasDiarias(filter));
    }
}
