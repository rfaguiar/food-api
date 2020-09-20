package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public record FormaPagamento (@Id
                              @GeneratedValue(strategy = GenerationType.IDENTITY)
                              Long id,
                              @Column(nullable = false)
                              String descricao) {
    public FormaPagamento() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "FormaPagamento{" +
                "id=" + id +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormaPagamento)) return false;
        FormaPagamento that = (FormaPagamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
