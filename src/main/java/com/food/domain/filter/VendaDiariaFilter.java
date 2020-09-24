package com.food.domain.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record VendaDiariaFilter(Long restauranteId,
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                LocalDateTime dataCriacaoInicio,
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                LocalDateTime dataCriacaoFim) {
}
