package com.food.domain.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ApiModel
public record VendaDiariaFilter(@ApiModelProperty(example = "1", value = "ID do restaurante")
                                Long restauranteId,
                                @ApiModelProperty(example = "2019-12-01T00:00:00Z",
                                        value = "Data/hora inicial da criação do pedido")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                LocalDateTime dataCriacaoInicio,
                                @ApiModelProperty(example = "2019-12-02T23:59:59Z",
                                        value = "Data/hora final da criação do pedido")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                LocalDateTime dataCriacaoFim) {
}
