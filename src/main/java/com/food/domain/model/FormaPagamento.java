package com.food.domain.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public record FormaPagamento (@Id
                              @GeneratedValue(strategy = GenerationType.IDENTITY)
                              Long id,
                              @Column(nullable = false)
                              String descricao,
                              @UpdateTimestamp
                              @Column(nullable = false, columnDefinition = "datetime")
                              LocalDateTime dataAtualizacao) {
    public FormaPagamento() {
        this(null, null, null);
    }

    public FormaPagamento(String descricao) {
        this(null, descricao, null);
    }

    @Override
    public String toString() {
        return "FormaPagamento{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormaPagamento that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao);
    }
}
