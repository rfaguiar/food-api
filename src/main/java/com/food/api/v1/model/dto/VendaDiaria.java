package com.food.api.v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public record VendaDiaria(@JsonProperty("data") Date data,
                          @JsonProperty("totalVendas") Long totalVendas,
                          @JsonProperty("totalFaturado") BigDecimal totalFaturado) {
}
