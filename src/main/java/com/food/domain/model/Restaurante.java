package com.food.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public record Restaurante (@Id
                          @GeneratedValue(strategy = GenerationType.IDENTITY)
                          Long id,
                          @Column(nullable = false)
                          String nome,
                          @Column(nullable = false)
                          BigDecimal taxaFrete,
                          @CreationTimestamp
                          @Column(nullable = false, columnDefinition = "datetime")
                          LocalDateTime dataCadastro,
                          @UpdateTimestamp
                          @Column(nullable = false, columnDefinition = "datetime")
                          LocalDateTime dataAtualizacao,
                          @Column(nullable = false)
                          Boolean ativo,
                          Boolean aberto,
                          @Embedded
                          Endereco endereco,
                          @ManyToOne
                          @JoinColumn(name = "cozinha_id", nullable = false)
                          Cozinha cozinha,
                          @ManyToMany
                          @JoinTable(name = "restaurante_forma_pagamento",
                                joinColumns = @JoinColumn(name = "restaurante_id"),
                                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
                          Set<FormaPagamento> formasPagamento,
                          @OneToMany(mappedBy = "restaurante")
                          Set<Produto> produtos,
                          @ManyToMany
                          @JoinTable(name = "restaurante_usuario_responsavel",
                                  joinColumns = @JoinColumn(name = "restaurante_id"),
                                  inverseJoinColumns = @JoinColumn(name = "usuario_id"))
                          Set<Usuario> responsaveis) {

    public Restaurante() {
        this(null, null, null, null, null, Boolean.TRUE, Boolean.TRUE,
                null, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "id=" + id +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurante)) return false;
        Restaurante that = (Restaurante) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.add(formaPagamento);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return responsaveis.remove(usuario);
    }

    public boolean adicionarResponsavel(Usuario usuario) {
        return responsaveis.add(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }
}
