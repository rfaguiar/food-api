package com.food.domain.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private BigDecimal taxaFrete;
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCadastro;
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;
    @Column(nullable = false)
    private Boolean ativo;
    private Boolean aberto;
    @Embedded
    private Endereco endereco;
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento;
    @OneToMany(mappedBy = "restaurante")
    private Set<Produto> produtos;
    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis;

    public Restaurante() {
        this(null, null, null, null, null, Boolean.TRUE, Boolean.TRUE,
                null, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public Restaurante(Long id, String nome, BigDecimal taxaFrete, LocalDateTime dataCadastro, LocalDateTime dataAtualizacao, Boolean ativo, Boolean aberto, Endereco endereco, Cozinha cozinha, Set<FormaPagamento> formasPagamento, Set<Produto> produtos, Set<Usuario> responsaveis) {
        this.id = id;
        this.nome = nome;
        this.taxaFrete = taxaFrete;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.ativo = ativo;
        this.aberto = aberto;
        this.endereco = endereco;
        this.cozinha = cozinha;
        this.formasPagamento = formasPagamento;
        this.produtos = produtos;
        this.responsaveis = responsaveis;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", taxaFrete=" + taxaFrete +
                ", dataCadastro=" + dataCadastro +
                ", dataAtualizacao=" + dataAtualizacao +
                ", ativo=" + ativo +
                ", aberto=" + aberto +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurante that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(taxaFrete, that.taxaFrete) &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(ativo, that.ativo) &&
                Objects.equals(aberto, that.aberto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, taxaFrete, dataCadastro, dataAtualizacao, ativo, aberto);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.remove(formaPagamento);
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

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cozinha getCozinha() {
        return cozinha;
    }

    public void setCozinha(Cozinha cozinha) {
        this.cozinha = cozinha;
    }

    public Set<FormaPagamento> getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(Set<FormaPagamento> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<Usuario> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(Set<Usuario> responsaveis) {
        this.responsaveis = responsaveis;
    }
}
