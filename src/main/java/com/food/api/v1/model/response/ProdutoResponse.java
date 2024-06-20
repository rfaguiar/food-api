package com.food.api.v1.model.response;

import com.food.domain.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
public class ProdutoResponse extends RepresentationModel<ProdutoResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Espetinho de Cupim")
    private String nome;
    @Schema(example = "Acompanha farinha, mandioca e vinagrete")
    private String descricao;
    @Schema(example = "12.50")
    private BigDecimal preco;
    @Schema(example = "true")
    private Boolean ativo;

    public ProdutoResponse(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getAtivo());
    }

    public ProdutoResponse(Long id, String nome, String descricao, BigDecimal preco, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
