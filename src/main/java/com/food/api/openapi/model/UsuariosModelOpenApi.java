package com.food.api.openapi.model;

import com.food.api.model.response.UsuarioResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsuarioModel")
public class UsuariosModelOpenApi {

    private UsuariosModelOpenApi.UsuarioEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("UsuarioEmbeddedModel")
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
