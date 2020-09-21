package com.food.domain.model;

public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private String descriao;

    StatusPedido(String descriao) {
        this.descriao = descriao;
    }

    public String getDescriao() {
        return descriao;
    }
}
