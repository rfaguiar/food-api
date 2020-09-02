package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record FormaPagamento (@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
                             String descricao) {
    public FormaPagamento() {
        this(null, null);
    }
}
