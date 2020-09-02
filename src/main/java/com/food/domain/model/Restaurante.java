package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public record Restaurante (@Id
                          @GeneratedValue(strategy = GenerationType.IDENTITY)
                          Long id,
                          String nome,
                          BigDecimal taxaFrete,
                          @ManyToOne
                          @JoinColumn(name = "cozinha_id")
                          Cozinha cozinha) {

    public Restaurante {}
}
