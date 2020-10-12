package com.food.api.v1.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.api.v1.assembler.FoodLinks;
import com.food.domain.model.Restaurante;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Optional;

@Relation(collectionRelation = "restaurantes")
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
        if (endereco != null && endereco.getCidade() != null) {
            endereco.getCidade().add(linkToCidade);
        }
        return this;
    }

    public RestauranteResponse addRestauranteStatusLink(FoodLinks foodLinks) {
        if (this.ativacaoPermitida()) {
            this.add(foodLinks.linkToRestauranteAtivacao(id, "ativar"));
        }
        if (this.inativacaoPermitida()) {
            this.add(foodLinks.linkToRestauranteInativacao(id, "inativar"));
        }
        if (this.aberturaPermitida()) {
            this.add(foodLinks.linkToRestauranteAbertura(id, "abrir"));
        }
        if (this.fechamentoPermitido()) {
            this.add(foodLinks.linkToRestauranteFechamento(id, "fechar"));
        }
        return this;
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isInativo() {
        return !isAtivo();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }

    public boolean fechamentoPermitido() {
        return isAberto();
    }

    public boolean aberturaPermitida() {
        return isAtivo() && isFechado();
    }

    public boolean ativacaoPermitida() {
        return isInativo();
    }
}
