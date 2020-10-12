package com.food.api.openapi.model;

import com.food.api.model.response.EstadoResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("EstadosModel")
public class EstadosModelOpenApi {

    private EstadosModelOpenApi.EstadoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("EstadosEmbeddedModel")
    public class EstadoEmbeddedModelOpenApi {

        private List<EstadoResponse> estados;

        public List<EstadoResponse> getEstados() {
            return estados;
        }

        public void setEstados(List<EstadoResponse> estados) {
            this.estados = estados;
        }
    }

    public EstadoEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(EstadoEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
