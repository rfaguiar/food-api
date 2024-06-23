package com.food.domain.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema
public record VendaDiariaFilter(@Schema(example = "1", description = "ID do restaurante")
                                Long restauranteId,
                                @Schema(example = "2019-12-01T00:00:00Z",
                                        description = "Data/hora inicial da criação do pedido")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                LocalDateTime dataCriacaoInicio,
                                @Schema(example = "2019-12-02T23:59:59Z",
                                        description = "Data/hora final da criação do pedido")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                LocalDateTime dataCriacaoFim) {
}
