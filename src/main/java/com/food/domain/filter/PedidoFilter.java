package com.food.domain.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema
public record PedidoFilter(@Schema(example = "1", description = "ID do cliente para filtro da pesquisa")
                           Long clienteId,
                           @Schema(example = "1", description = "ID do restaurante para filtro da pesquisa")
                           Long restauranteId,
                           @Schema(example = "2019-10-30T00:00:00Z",
                                   description = "Data/hora de criação inicial para filtro da pesquisa")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                           LocalDateTime dataCriacaoInicio,
                           @Schema(example = "2019-11-01T10:00:00Z",
                                   description = "Data/hora de criação final para filtro da pesquisa")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                           LocalDateTime dataCriacaoFim) {
}
