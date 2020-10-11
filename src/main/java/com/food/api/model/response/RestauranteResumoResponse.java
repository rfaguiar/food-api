package com.food.api.model.response;

import com.food.domain.model.Restaurante;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;

public class RestauranteResumoResponse extends RepresentationModel<RestauranteResumoResponse> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    public RestauranteResumoResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome());
    }

    public RestauranteResumoResponse(Long id, String nome) {
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
