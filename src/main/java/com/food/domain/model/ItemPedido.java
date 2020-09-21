package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public record ItemPedido(@Id
                         @GeneratedValue(strategy = GenerationType.IDENTITY)
                         Long id,
                         BigDecimal precoUnitario,
                         BigDecimal precoTotal,
                         Integer quantidade,
                         String observacao,
                         @ManyToOne
                         @JoinColumn(nullable = false)
                         Pedido pedido,
                         @ManyToOne
                         @JoinColumn(nullable = false)
                         Produto produto) {
    public ItemPedido() {
        this(null, null, null, null, null, null, null);
    }

    public static ItemPedido calcularPrecoTotal(ItemPedido itemPedido) {
        BigDecimal precoUnitario = itemPedido.precoUnitario();
        Integer quantidade = itemPedido.quantidade();

        if (precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }

        if (quantidade == null) {
            quantidade = 0;
        }

        BigDecimal precoTotal = precoUnitario.multiply(new BigDecimal(quantidade));
        return new ItemPedido(itemPedido.id(), itemPedido.precoUnitario(), precoTotal,
                itemPedido.quantidade(),itemPedido.observacao(), itemPedido.pedido(), itemPedido.produto());
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
}
