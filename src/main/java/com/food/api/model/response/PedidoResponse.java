package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.api.assembler.FoodLinks;
import com.food.domain.model.Pedido;
import com.food.domain.model.StatusPedido;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Relation("pedidos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoResponse extends RepresentationModel<PedidoResponse> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;
    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;
    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;
    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;
    @ApiModelProperty(example = "CRIADO")
    private String status;
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private LocalDateTime dataCriacao;
    @ApiModelProperty(example = "2019-12-01T20:35:10Z")
    private LocalDateTime dataConfirmacao;
    @ApiModelProperty(example = "2019-12-01T20:55:30Z")
    private LocalDateTime dataEntrega;
    @ApiModelProperty(example = "2019-12-01T20:35:00Z")
    private LocalDateTime dataCancelamento;
    private RestauranteApenasNomeResponse restaurante;
    private UsuarioResponse cliente;
    private FormaPagamentoResponse formaPagamento;
    private EnderecoResponse enderecoEntrega;
    private List<ItemPedidoResponse> itens;

    public PedidoResponse(Pedido pedido) {
        this(pedido.getCodigo(), pedido.getSubtotal(), pedido.getTaxaFrete(), pedido.getValorTotal(),
                pedido.getStatus().name(), pedido.getDataCriacao(), pedido.getDataConfirmacao(),
                pedido.getDataEntrega(), pedido.getDataCancelamento(),
                new RestauranteApenasNomeResponse(pedido.getRestaurante()),
                new UsuarioResponse(pedido.getCliente()),
                new FormaPagamentoResponse(pedido.getFormaPagamento()),
                new EnderecoResponse(pedido.getEnderecoEntrega()),
                pedido.getItens().stream().map(ItemPedidoResponse::new).collect(Collectors.toList())
        );
    }

    public PedidoResponse(String codigo, BigDecimal subtotal, BigDecimal taxaFrete, BigDecimal valorTotal,
                          String status, LocalDateTime dataCriacao, LocalDateTime dataConfirmacao,
                          LocalDateTime dataEntrega, LocalDateTime dataCancelamento,
                          RestauranteApenasNomeResponse restaurante, UsuarioResponse cliente,
                          FormaPagamentoResponse formaPagamento, EnderecoResponse enderecoEntrega,
                          List<ItemPedidoResponse> itens) {
        this.codigo = codigo;
        this.subtotal = subtotal;
        this.taxaFrete = taxaFrete;
        this.valorTotal = valorTotal;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataConfirmacao = dataConfirmacao;
        this.dataEntrega = dataEntrega;
        this.dataCancelamento = dataCancelamento;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
        this.enderecoEntrega = enderecoEntrega;
        this.itens = itens;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public RestauranteApenasNomeResponse getRestaurante() {
        return restaurante;
    }

    public UsuarioResponse getCliente() {
        return cliente;
    }

    public FormaPagamentoResponse getFormaPagamento() {
        return formaPagamento;
    }

    public EnderecoResponse getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public List<ItemPedidoResponse> getItens() {
        return itens;
    }

    public PedidoResponse addRestauranteLink(Link linkToRestaurante) {
        restaurante.add(linkToRestaurante);
        return this;
    }

    public PedidoResponse addClientLink(Link linkToUsuario) {
        cliente.add(linkToUsuario);
        return this;
    }

    public PedidoResponse addFormaPagamentoLink(Link linkToFormaPagamento) {
        formaPagamento.add(linkToFormaPagamento);
        return this;
    }

    public PedidoResponse addCidadeEnderecoLink(Link linkToCidade) {
        enderecoEntrega.getCidade().add(linkToCidade);
        return this;
    }

    public PedidoResponse addItensLink(FoodLinks foodLinks) {
        itens.forEach(addItem(foodLinks));
        return this;
    }

    private Consumer<ItemPedidoResponse> addItem(FoodLinks foodLinks) {
        return item -> item.add(foodLinks.linkToProduto(restaurante.getId(), item.getProdutoId(), "produto"));
    }

    public PedidoResponse addStatusLink(FoodLinks foodLinks) {
        if (this.podeSerConfirmado()) {
            this.add(foodLinks.linkToConfirmacaoPedido(this.codigo));
        }
        if (this.podeSerEntregue()) {
            this.add(foodLinks.linkToEntregaPedido(this.codigo));
        }
        if (this.podeSerCancelado()) {
            this.add(foodLinks.linkToCancelamentoPedido(this.codigo));
        }
        return this;
    }

    public boolean podeSerConfirmado() {
        return StatusPedido.valueOf(status).podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerEntregue() {
        return StatusPedido.valueOf(status).podeAlterarPara(StatusPedido.ENTREGUE);
    }

    public boolean podeSerCancelado() {
        return StatusPedido.valueOf(status).podeAlterarPara(StatusPedido.CANCELADO);
    }
}
