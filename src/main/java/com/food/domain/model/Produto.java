package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public record Produto(@Id
                      @GeneratedValue(strategy = GenerationType.IDENTITY)
                      Long id,
                      String nome,
                      String descricao,
                      BigDecimal preco,
                      Boolean ativo,
                      @ManyToOne
                      @JoinColumn(name = "restaurante_id")
                      Restaurante restaurante) {
    public Produto() {
        this(null, null, null, null, null, null);
    }
}
