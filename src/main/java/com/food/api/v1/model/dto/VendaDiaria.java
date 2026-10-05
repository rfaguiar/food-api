package com.food.api.v1.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class VendaDiaria {
    @JsonProperty("data")
    private Date data;
    @JsonProperty("totalVendas")
    private Long totalVendas;
    @JsonProperty("totalFaturado")
    private BigDecimal totalFaturado;

    public VendaDiaria(java.sql.Date data, Long totalVendas, BigDecimal totalFaturado) {
        this.data = new Date(data.getTime());
        this.totalVendas = totalVendas;
        this.totalFaturado = totalFaturado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(Long totalVendas) {
        this.totalVendas = totalVendas;
    }

    public BigDecimal getTotalFaturado() {
        return totalFaturado;
    }

    public void setTotalFaturado(BigDecimal totalFaturado) {
        this.totalFaturado = totalFaturado;
    }
}
