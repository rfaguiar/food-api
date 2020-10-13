package com.food.api.v2.openapi.model;

import com.food.api.v2.model.response.CidadeResponseV2;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
public class CidadesModelV2OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    public class CidadesEmbeddedModelOpenApi {

        private List<CidadeResponseV2> cidades;

        public List<CidadeResponseV2> getCidades() {
            return cidades;
        }

        public void setCidades(List<CidadeResponseV2> cidades) {
            this.cidades = cidades;
        }
    }

    public CidadesEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(CidadesEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
