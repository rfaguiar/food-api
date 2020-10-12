package com.food.api.controller;

import com.food.api.assembler.FoodLinks;
import com.food.api.model.dto.VendaDiaria;
import com.food.api.model.response.EstatisticasResponse;
import com.food.api.openapi.controller.EstatisticasControllerOpenApi;
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
public class EstatisticasController implements EstatisticasControllerOpenApi {

    private final VendaQueryService vendaQueryService;
    private final VendaReportService vendaReportService;
    private final FoodLinks foodLinks;

    @Autowired
    public EstatisticasController(VendaQueryService vendaQueryService, VendaReportService vendaReportService, FoodLinks foodLinks) {
        this.vendaQueryService = vendaQueryService;
        this.vendaReportService = vendaReportService;
        this.foodLinks = foodLinks;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasResponse estatisticas() {
        return new EstatisticasResponse()
                .add(foodLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
    }

    @Override
    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        return vendaQueryService.consultarVendasDiarias(filter);
    }

    @Override
    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf")
                .body(vendaReportService.consultarVendasDiarias(filter));
    }
}
