package com.food.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(nullable = false)
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    public Produto() {
        this(null, null, null, null, null, null);
    }

    public Produto(Long id, String nome, String descricao, BigDecimal preco, Boolean ativo, Restaurante restaurante) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.ativo = ativo;
        this.restaurante = restaurante;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", ativo=" + ativo +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto produto)) return false;
        return Objects.equals(id, produto.id) &&
                Objects.equals(nome, produto.nome) &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(preco, produto.preco) &&
                Objects.equals(ativo, produto.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, preco, ativo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}
