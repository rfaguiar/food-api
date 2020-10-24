package com.food.domain.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ApiModel
public record PedidoFilter(@ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
                           Long clienteId,
                           @ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
                           Long restauranteId,
                           @ApiModelProperty(example = "2019-10-30T00:00:00Z",
                                   value = "Data/hora de criação inicial para filtro da pesquisa")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                           LocalDateTime dataCriacaoInicio,
                           @ApiModelProperty(example = "2019-11-01T10:00:00Z",
                                   value = "Data/hora de criação final para filtro da pesquisa")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                           LocalDateTime dataCriacaoFim) {
}
