package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.CidadeResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    public class CidadeEmbeddedModelOpenApi {

        private List<CidadeResponse> cidades;

        public List<CidadeResponse> getCidades() {
            return cidades;
        }

        public void setCidades(List<CidadeResponse> cidades) {
            this.cidades = cidades;
        }
    }

    public CidadeEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(CidadeEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
