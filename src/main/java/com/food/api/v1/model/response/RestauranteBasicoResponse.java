package com.food.api.v1.model.response;

import com.food.domain.model.Restaurante;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
public class RestauranteBasicoResponse extends RepresentationModel<RestauranteBasicoResponse> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;

    @Schema(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaResponse cozinha;

    public RestauranteBasicoResponse(Restaurante restaurante) {
        this(restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete(),
                new CozinhaResponse(restaurante.getCozinha()));
    }

    public RestauranteBasicoResponse(Long id, String nome, BigDecimal taxaFrete, CozinhaResponse cozinha) {
        this.id = id;
        this.nome = nome;
        this.taxaFrete = taxaFrete;
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

    public CozinhaResponse getCozinha() {
        return cozinha;
    }

    public RestauranteBasicoResponse addCozinhaLink(Link linkToCozinha) {
        cozinha.add(linkToCozinha);
        return this;
    }
}
