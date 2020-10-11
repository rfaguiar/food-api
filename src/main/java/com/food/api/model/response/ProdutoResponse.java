package com.food.api.model.response;

import com.food.domain.model.Produto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class ProdutoResponse {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Espetinho de Cupim")
    private String nome;
    @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
    private String descricao;
    @ApiModelProperty(example = "12.50")
    private BigDecimal preco;
    @ApiModelProperty(example = "true")
    private Boolean ativo;

    public ProdutoResponse(Produto produto) {
        this(produto.id(), produto.nome(), produto.descricao(), produto.preco(), produto.ativo());
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
