package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.dto.VendaDiaria;
import com.food.api.v1.model.response.EstatisticasResponse;
import com.food.domain.filter.VendaDiariaFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.food.config.OpenApiConfig.TAG_ESTATISTICAS;

@Tag(name = TAG_ESTATISTICAS)
public interface EstatisticasControllerOpenApi {


    @Operation(description = "Estatísticas", hidden = true)
    EstatisticasResponse estatisticas();

    @Operation(summary = "Consulta estatísticas de vendas diárias")
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter);

    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter);
}
