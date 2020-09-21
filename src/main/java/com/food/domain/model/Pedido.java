package com.food.domain.model;

import com.food.domain.exception.NegocioException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import java.util.stream.Collectors;

@Entity
public record Pedido(@Id
                     @GeneratedValue(strategy = GenerationType.IDENTITY)
                     Long id,
                     String codigo,
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
                     @ManyToOne(fetch = FetchType.LAZY)
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
        this(null, null, null, null, null, null,
                StatusPedido.CRIADO, null, null, null,
                null, null, null, null, new HashSet<>());
    }

    public Pedido calcularValorTotal() {
        Set<ItemPedido> itensCalculados = itens.stream().map(ItemPedido::calcularPrecoTotal).collect(Collectors.toSet());

        BigDecimal subtotal = itensCalculados.stream()
                .map(ItemPedido::precoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorTotal = subtotal.add(taxaFrete);
        return new Pedido(id, codigo, subtotal, taxaFrete, valorTotal, enderecoEntrega,
                status, dataCriacao, dataConfirmacao, dataCancelamento,
                dataEntrega, formaPagamento, restaurante, cliente, itensCalculados);
    }

    public Pedido confirmar() {
        StatusPedido novoStatus = validarStatusPedido(StatusPedido.CONFIRMADO);
        return new Pedido(id, codigo, subtotal, taxaFrete, valorTotal,
                enderecoEntrega, novoStatus, dataCriacao, LocalDateTime.now(),
                dataCancelamento, dataEntrega, formaPagamento, restaurante,
                cliente, itens);
    }

    public Pedido entregar() {
        StatusPedido novoStatus = validarStatusPedido(StatusPedido.ENTREGUE);
        return new Pedido(id, codigo, subtotal, taxaFrete, valorTotal,
                enderecoEntrega, novoStatus, dataCriacao, dataConfirmacao,
                dataCancelamento, LocalDateTime.now(), formaPagamento, restaurante,
                cliente, itens);
    }

    public Pedido cancelar() {
        StatusPedido novoStatus = validarStatusPedido(StatusPedido.CANCELADO);
        return new Pedido(id, codigo, subtotal, taxaFrete, valorTotal,
                enderecoEntrega, novoStatus, dataCriacao, dataConfirmacao,
                LocalDateTime.now(), dataEntrega, formaPagamento, restaurante,
                cliente, itens);
    }

    private StatusPedido validarStatusPedido(StatusPedido novoStatus) {
        if (status.naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            codigo, status.getDescricao(),
                            novoStatus.getDescricao()));
        }
        return novoStatus;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", subtotal=" + subtotal +
                ", taxaFrete=" + taxaFrete +
                ", valorTotal=" + valorTotal +
                ", status=" + status +
                ", dataCriacao=" + dataCriacao +
                ", dataConfirmacao=" + dataConfirmacao +
                ", dataCancelamento=" + dataCancelamento +
                ", dataEntrega=" + dataEntrega +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals(id, pedido.id) &&
                Objects.equals(subtotal, pedido.subtotal) &&
                Objects.equals(taxaFrete, pedido.taxaFrete) &&
                Objects.equals(valorTotal, pedido.valorTotal) &&
                status == pedido.status &&
                Objects.equals(dataCriacao, pedido.dataCriacao) &&
                Objects.equals(dataConfirmacao, pedido.dataConfirmacao) &&
                Objects.equals(dataCancelamento, pedido.dataCancelamento) &&
                Objects.equals(dataEntrega, pedido.dataEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subtotal, taxaFrete, valorTotal, status, dataCriacao, dataConfirmacao, dataCancelamento, dataEntrega);
    }
}
