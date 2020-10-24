package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.dto.VendaDiaria;
import com.food.api.v1.model.response.EstatisticasResponse;
import com.food.domain.filter.VendaDiariaFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.food.config.OpenApiConfig.TAG_ESTATISTICAS;

@Api(tags = TAG_ESTATISTICAS)
public interface EstatisticasControllerOpenApi {


    @ApiOperation(value = "Estatísticas", hidden = true)
    EstatisticasResponse estatisticas();

    @ApiOperation("Consulta estatísticas de vendas diárias")
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter);

    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter);
}
