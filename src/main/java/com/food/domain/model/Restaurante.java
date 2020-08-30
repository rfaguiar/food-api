package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public record Restaurante (@Id
                          @GeneratedValue(strategy = GenerationType.IDENTITY)
                          Long id,
                          String nome,
                          BigDecimal taxaFrete,
                          @ManyToOne
                          Cozinha cozinha) {

    public Restaurante() {
        this(null, null, null, null);
    }
}
