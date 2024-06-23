package com.food.domain.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String descricao;
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    LocalDateTime dataAtualizacao;

    public FormaPagamento() {
        this(null, null, null);
    }

    public FormaPagamento(Long id, String descricao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.descricao = descricao;
        this.dataAtualizacao = dataAtualizacao;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
