package com.food.api.v1.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.domain.model.Cidade;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Optional;

@Relation(collectionRelation = "cidades")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CidadeResumoResponse extends RepresentationModel<CidadeResumoResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Uberlândia")
    private String nome;
    @Schema(example = "Minas Gerais")
    private String estado;

    public CidadeResumoResponse(Cidade cidade) {
        this(cidade.getId(), cidade.getNome(), cidade.getEstado().getNome());
    }

    public CidadeResumoResponse(Optional<Cidade> cidade) {
        this(cidade.map(Cidade::getId).orElse(null),
                cidade.map(Cidade::getNome).orElse(null),
                cidade.map(c -> c.getEstado().getNome()).orElse(null));
    }

    public CidadeResumoResponse(Long id, String nome, String estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
    }
}
