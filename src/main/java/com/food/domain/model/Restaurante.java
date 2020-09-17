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
                          @ManyToOne(fetch = FetchType.LAZY)
                          @JoinColumn(name = "cozinha_id", nullable = false)
                          Cozinha cozinha,
                          @ManyToMany
                          @JoinTable(name = "restaurante_forma_pagamento",
                                joinColumns = @JoinColumn(name = "restaurante_id"),
                                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
                          Set<FormaPagamento> formasPagamento,
                          @OneToMany(mappedBy = "restaurante")
                          Set<Produto> produtos) {

    public Restaurante() {
        this(null, null, null, null, null, Boolean.TRUE, Boolean.TRUE,
                null, null, new HashSet<>(), new HashSet<>());
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.add(formaPagamento);
    }
}
