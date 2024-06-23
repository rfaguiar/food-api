package com.food.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal precoUnitario;
    BigDecimal precoTotal;
    Integer quantidade;
    String observacao;
    @ManyToOne
    @JoinColumn(nullable = false)
    Pedido pedido;
    @ManyToOne
    @JoinColumn(nullable = false)
    Produto produto;

    public ItemPedido() {
        this(null, null, null, null, null, null, null);
    }

    public ItemPedido(Long id, BigDecimal precoUnitario, BigDecimal precoTotal, Integer quantidade, String observacao, Pedido pedido, Produto produto) {
        this.id = id;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.pedido = pedido;
        this.produto = produto;
    }

    public static ItemPedido calcularPrecoTotal(ItemPedido itemPedido) {
        BigDecimal precoUnitario = itemPedido.getPrecoUnitario();
        Integer quantidade = itemPedido.getQuantidade();

        if (precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }

        if (quantidade == null) {
            quantidade = 0;
        }

        BigDecimal precoTotal = precoUnitario.multiply(new BigDecimal(quantidade));
        return new ItemPedido(itemPedido.getId(), itemPedido.getPrecoUnitario(), precoTotal,
                itemPedido.getQuantidade(),itemPedido.getObservacao(), itemPedido.getPedido(), itemPedido.getProduto());
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                ", precoUnitario=" + precoUnitario +
                ", precoTotal=" + precoTotal +
                ", quantidade=" + quantidade +
                ", observacao='" + observacao + '\'' +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(precoUnitario, that.precoUnitario) &&
                Objects.equals(precoTotal, that.precoTotal) &&
                Objects.equals(quantidade, that.quantidade) &&
                Objects.equals(observacao, that.observacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, precoUnitario, precoTotal, quantidade, observacao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
