package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.domain.model.Restaurante;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Optional;

@Relation("restaurantes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestauranteResponse extends RepresentationModel<RestauranteResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;
    @ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoResponse endereco;
    private CozinhaResponse cozinha;

    public RestauranteResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(), restaurante.ativo(), restaurante.aberto(),
                Optional.ofNullable(restaurante.endereco()).map(EnderecoResponse::new).orElse(null),
                new CozinhaResponse(restaurante.cozinha()));
    }

    public RestauranteResponse(Long id, String nome, BigDecimal taxaFrete, Boolean ativo, Boolean aberto, EnderecoResponse endereco, CozinhaResponse cozinha) {
        this.id = id;
        this.nome = nome;
        this.taxaFrete = taxaFrete;
        this.ativo = ativo;
        this.aberto = aberto;
        this.endereco = endereco;
        this.cozinha = cozinha;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public EnderecoResponse getEndereco() {
        return endereco;
    }

    public CozinhaResponse getCozinha() {
        return cozinha;
    }

    public RestauranteResponse addCozinhaLink(Link linkToCozinha) {
        cozinha.add(linkToCozinha);
        return this;
    }

    public RestauranteResponse addCidadeEnderecoLink(Link linkToCidade) {
        endereco.getCidade().add(linkToCidade);
        return this;
    }
}
