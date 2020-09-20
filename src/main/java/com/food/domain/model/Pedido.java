package com.food.domain.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public record Pedido(@Id
                     @GeneratedValue(strategy = GenerationType.IDENTITY)
                     Long id,
                     BigDecimal subtotal,
                     BigDecimal taxaFrete,
                     BigDecimal valorTotal,
                     @Embedded
                     Endereco enderecoEntrega,
                     @Enumerated(EnumType.STRING)
                     StatusPedido status,
                     @CreationTimestamp
                     LocalDateTime dataCriacao,
                     LocalDateTime dataConfirmacao,
                     LocalDateTime dataCancelamento,
                     LocalDateTime dataEntrega,
                     @ManyToOne
                     @JoinColumn(nullable = false)
                     FormaPagamento formaPagamento,
                     @ManyToOne
                     @JoinColumn(nullable = false)
                     Restaurante restaurante,
                     @ManyToOne
                     @JoinColumn(name = "usuario_cliente_id", nullable = false)
                     Usuario cliente,
                     @OneToMany(mappedBy = "pedido")
                     Set<ItemPedido> itens) {

    public Pedido() {
        this(null, null, null, null, null,
                StatusPedido.CRIADO, null, null, null,
                null, null, null, null, new HashSet<>());
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido that = (Pedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
