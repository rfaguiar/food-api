package com.food.domain.model;

import com.food.domain.exception.NegocioException;
import com.food.event.PedidoConfirmadoEvent;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

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
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {}

    public Pedido(Long id, String codigo, BigDecimal subtotal, BigDecimal taxaFrete, BigDecimal valorTotal, Endereco enderecoEntrega, StatusPedido status, LocalDateTime dataCriacao, LocalDateTime dataConfirmacao, LocalDateTime dataCancelamento, LocalDateTime dataEntrega, FormaPagamento formaPagamento, Restaurante restaurante, Usuario cliente, Set<ItemPedido> itens) {
        this.id = id;
        this.codigo = codigo;
        this.subtotal = subtotal;
        this.taxaFrete = taxaFrete;
        this.valorTotal = valorTotal;
        this.enderecoEntrega = enderecoEntrega;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataConfirmacao = dataConfirmacao;
        this.dataCancelamento = dataCancelamento;
        this.dataEntrega = dataEntrega;
        this.formaPagamento = formaPagamento;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.itens = itens;
    }

    public Pedido calcularValorTotal() {
        Set<ItemPedido> itensCalculados = itens.stream().map(ItemPedido::calcularPrecoTotal).collect(Collectors.toSet());

        BigDecimal subTotal = itensCalculados.stream()
                .map(ItemPedido::precoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valorTotal = subTotal.add(taxaFrete);

        itens = itensCalculados;
        subtotal = subTotal;
        return this;
    }

    public Pedido confirmar() {
        status = validarStatusPedido(StatusPedido.CONFIRMADO);
        dataConfirmacao = LocalDateTime.now();
        registerEvent(new PedidoConfirmadoEvent(this));
        return this;
    }

    public Pedido entregar() {
        status = validarStatusPedido(StatusPedido.ENTREGUE);
        dataEntrega = LocalDateTime.now();
        return this;
    }

    public Pedido cancelar() {
        status = validarStatusPedido(StatusPedido.CANCELADO);
        dataCancelamento = LocalDateTime.now();
        return this;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) &&
                Objects.equals(codigo, pedido.codigo) &&
                Objects.equals(subtotal, pedido.subtotal) &&
                Objects.equals(taxaFrete, pedido.taxaFrete) &&
                Objects.equals(valorTotal, pedido.valorTotal) &&
                Objects.equals(enderecoEntrega, pedido.enderecoEntrega) &&
                status == pedido.status &&
                Objects.equals(dataCriacao, pedido.dataCriacao) &&
                Objects.equals(dataConfirmacao, pedido.dataConfirmacao) &&
                Objects.equals(dataCancelamento, pedido.dataCancelamento) &&
                Objects.equals(dataEntrega, pedido.dataEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, subtotal, taxaFrete, valorTotal, enderecoEntrega, status, dataCriacao, dataConfirmacao, dataCancelamento, dataEntrega);
    }
}
