package com.food.api.v1.model.response;

import com.food.domain.model.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

public class EstadoResponse extends RepresentationModel<EstadoResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "São Paulo")
    private String nome;

    public EstadoResponse(Estado estado) {
        this(estado.getId(), estado.getNome());
    }

    public EstadoResponse(Long id, String nome) {
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
