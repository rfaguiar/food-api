package com.food.api.v1.model.response;

import com.food.domain.model.Restaurante;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
public class RestauranteApenasNomeResponse extends RepresentationModel<RestauranteApenasNomeResponse> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;

    public RestauranteApenasNomeResponse(Restaurante restaurante) {
        this(restaurante.getId(), restaurante.getNome());
    }

    public RestauranteApenasNomeResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
