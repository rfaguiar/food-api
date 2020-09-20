package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public record Cozinha (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,
    @Column(nullable = false)
    String nome,
    @OneToMany(mappedBy = "cozinha")
    Set<Restaurante> restaurantes) {

    public Cozinha() {
        this(null, null, new HashSet<>());
    }

    @Override
    public String toString() {
        return "Cozinha{" +
                "id=" + id +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cozinha)) return false;
        Cozinha that = (Cozinha) o;
        return Objects.equals(id, that.id);
    }
}
