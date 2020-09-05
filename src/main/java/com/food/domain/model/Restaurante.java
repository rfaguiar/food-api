package com.food.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
                          String nome,
                          BigDecimal taxaFrete,
                          @CreationTimestamp
                          @Column(nullable = false, columnDefinition = "datetime")
                          LocalDateTime dataCadastro,
                          @UpdateTimestamp
                          @Column(nullable = false, columnDefinition = "datetime")
                          LocalDateTime dataAtualizacao,
                          @Embedded
                          Endereco endereco,
                          @ManyToOne
                          @JoinColumn(name = "cozinha_id")
                          Cozinha cozinha,
                          @ManyToMany
                          @JoinTable(name = "restaurante_forma_pagamento",
                                joinColumns = @JoinColumn(name = "restaurante_id"),
                                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
                          Set<FormaPagamento> formasPagamento,
                          @OneToMany(mappedBy = "restaurante")
                          Set<Produto> produtos) {

    public Restaurante() {
        this(null, null, null, null, null,
                null, null, new HashSet<>(), new HashSet<>());
    }
}
