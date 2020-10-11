package com.food.api.model.response;

import com.food.domain.model.ItemPedido;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation("ItensPedido")
public class ItemPedidoResponse extends RepresentationModel<ItemPedidoResponse> {

    @ApiModelProperty(example = "1")
    private Long produtoId;
    @ApiModelProperty(example = "Porco com molho agridoce")
    private String produtoNome;
    @ApiModelProperty(example = "2")
    private Integer quantidade;
    @ApiModelProperty(example = "78.90")
    private BigDecimal precoUnitario;
    @ApiModelProperty(example = "157.80")
    private BigDecimal precoTotal;
    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;

    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.produto().id(), itemPedido.produto().nome(), itemPedido.quantidade(), itemPedido.precoUnitario(),
                itemPedido.precoTotal(), itemPedido.observacao());
    }

    public ItemPedidoResponse(Long produtoId, String produtoNome, Integer quantidade, BigDecimal precoUnitario, BigDecimal precoTotal, String observacao) {
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.precoTotal = precoTotal;
        this.observacao = observacao;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public String getObservacao() {
        return observacao;
    }
}
