package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record Estado (@Id
                      @GeneratedValue(strategy = GenerationType.IDENTITY)
                      Long id,
                      @Column(nullable = false)
                      String nome) {
    public Estado() {
        this(null, null);
    }
}
