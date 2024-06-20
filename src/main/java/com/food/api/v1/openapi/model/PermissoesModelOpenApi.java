package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.PermissaoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(defaultValue = "PermissoesModel")
public class PermissoesModelOpenApi {

    private PermissoesModelOpenApi.PermissaoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(defaultValue = "PermissoesEmbeddedModel")
    public class PermissaoEmbeddedModelOpenApi {

        private List<PermissaoResponse> permissoes;

        public List<PermissaoResponse> getPermissoes() {
            return permissoes;
        }

        public void setPermissoes(List<PermissaoResponse> permissoes) {
            this.permissoes = permissoes;
        }
    }

    public PermissaoEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(PermissaoEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
