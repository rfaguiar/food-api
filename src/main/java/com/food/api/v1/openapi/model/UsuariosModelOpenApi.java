package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(defaultValue = "UsuarioModel")
public class UsuariosModelOpenApi {

    private UsuariosModelOpenApi.UsuarioEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(defaultValue = "UsuarioEmbeddedModel")
    public class UsuarioEmbeddedModelOpenApi {

        private List<UsuarioResponse> usuarios;

        public List<UsuarioResponse> getUsuarios() {
            return usuarios;
        }

        public void setUsuarios(List<UsuarioResponse> usuarios) {
            this.usuarios = usuarios;
        }
    }

    public UsuarioEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(UsuarioEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
