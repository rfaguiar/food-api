package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record Permissao (@Id
                         @GeneratedValue(strategy = GenerationType.IDENTITY)
                         Long id,
                         @Column(nullable = false)
                         String nome,
                         @Column(nullable = false)
                         String descricao) {

    public Permissao() {
        this(null, null, null);
    }
}
