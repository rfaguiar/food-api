package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.domain.model.Pedido;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Relation(collectionRelation = "pedidos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResumoResponse extends RepresentationModel<PedidoResumoResponse> {

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
    private RestauranteApenasNomeResponse restaurante;
    private UsuarioResponse cliente;

    public PedidoResumoResponse(Pedido pedido) {
        this(pedido.getCodigo(), pedido.getSubtotal(), pedido.getTaxaFrete(), pedido.getValorTotal(),
                pedido.getStatus().name(), pedido.getDataCriacao(),
                new RestauranteApenasNomeResponse(pedido.getRestaurante()),
                new UsuarioResponse(pedido.getCliente())
        );
    }

    public PedidoResumoResponse(String codigo, BigDecimal subtotal, BigDecimal taxaFrete, BigDecimal valorTotal,
                                String status, LocalDateTime dataCriacao, RestauranteApenasNomeResponse restaurante,
                                UsuarioResponse cliente) {
        this.codigo = codigo;
        this.subtotal = subtotal;
        this.taxaFrete = taxaFrete;
        this.valorTotal = valorTotal;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.restaurante = restaurante;
        this.cliente = cliente;
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

    public RestauranteApenasNomeResponse getRestaurante() {
        return restaurante;
    }

    public UsuarioResponse getCliente() {
        return cliente;
    }

    public PedidoResumoResponse addRestauranteLink(Link linkToRestaurante) {
        restaurante.add(linkToRestaurante);
        return this;
    }

    public PedidoResumoResponse addClientLink(Link linkToUsuario) {
        cliente.add(linkToUsuario);
        return this;
    }
}
