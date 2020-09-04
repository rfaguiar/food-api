package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public record Cozinha (
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    String nome,
    @OneToMany(mappedBy = "cozinha")
    Set<Restaurante> restaurantes) {

    public Cozinha() {
        this(null, null, new HashSet<>());
    }
}
