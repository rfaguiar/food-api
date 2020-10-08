package com.food.api.openapi.controller;

import com.food.api.model.dto.VendaDiaria;
import com.food.domain.filter.VendaDiariaFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

import static com.food.config.OpenApiConfig.TAG_ESTATISTICAS;

@Api(tags = TAG_ESTATISTICAS)
public interface EstatisticasControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante",
                    example = "1", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z", dataTypeClass = LocalDateTime.class)
    })
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter);

    @GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter);
}
