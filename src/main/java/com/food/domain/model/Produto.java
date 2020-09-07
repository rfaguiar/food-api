package com.food.domain.model;

import javax.persistence.Column;
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
                      @Column(nullable = false)
                      String nome,
                      @Column(nullable = false)
                      String descricao,
                      @Column(nullable = false)
                      BigDecimal preco,
                      @Column(nullable = false)
                      Boolean ativo,
                      @ManyToOne
                      @JoinColumn(nullable = false)
                      Restaurante restaurante) {
    public Produto() {
        this(null, null, null, null, null, null);
    }
}
