package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public record Restaurante (@Id
                          @GeneratedValue(strategy = GenerationType.IDENTITY)
                          Long id,
                          String nome,
                          BigDecimal taxaFrete,
                          @ManyToOne
                          @JoinColumn(name = "cozinha_id")
                          Cozinha cozinha,
                          @ManyToMany
                          @JoinTable(name = "restaurante_forma_pagamento",
                                joinColumns = @JoinColumn(name = "restaurante_id"),
                                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
                          Set<FormaPagamento> formasPagamento) {

    public Restaurante() {
        this(null, null, null, null, new HashSet<>());
    }
}
