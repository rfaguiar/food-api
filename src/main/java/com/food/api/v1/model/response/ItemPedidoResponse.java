package com.food.api.v1.model.response;

import com.food.domain.model.ItemPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "ItensPedido")
public class ItemPedidoResponse extends RepresentationModel<ItemPedidoResponse> {

    @Schema(example = "1")
    private Long produtoId;
    @Schema(example = "Porco com molho agridoce")
    private String produtoNome;
    @Schema(example = "2")
    private Integer quantidade;
    @Schema(example = "78.90")
    private BigDecimal precoUnitario;
    @Schema(example = "157.80")
    private BigDecimal precoTotal;
    @Schema(example = "Menos picante, por favor")
    private String observacao;

    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.getProduto().getId(), itemPedido.getProduto().getNome(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario(),
                itemPedido.getPrecoTotal(), itemPedido.getObservacao());
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
