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

    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido)) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
