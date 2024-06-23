package com.food.api.v1.model.response;

import com.food.domain.model.Permissao;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
public class PermissaoResponse extends RepresentationModel<PermissaoResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "CONSULTAR_COZINHAS")
    private String nome;
    @Schema(example = "Permite consultar cozinhas")
    private String descricao;

    public PermissaoResponse(Permissao permissao) {
        this(permissao.getId(), permissao.getNome(), permissao.getDescricao());
    }

    public PermissaoResponse(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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
}
