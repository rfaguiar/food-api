package com.food.domain.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record PedidoFilter(Long clienteId,
                           Long restauranteId,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                           LocalDateTime dataCriacaoInicio,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                           LocalDateTime dataCriacaoFim) {
}
