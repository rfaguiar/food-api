package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record Cozinha (
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String nome) {

    public Cozinha() {
        this(null, null);
    }
}