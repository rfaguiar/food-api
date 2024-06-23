package com.food.api.v3.openapi.model;

import com.food.api.v3.model.response.CidadeResponseV3;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(defaultValue = "CidadesModel")
public class CidadesModelV3OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(defaultValue = "CidadesEmbeddedModel")
    public class CidadesEmbeddedModelOpenApi {

        private List<CidadeResponseV3> cidades;

        public List<CidadeResponseV3> getCidades() {
            return cidades;
        }

        public void setCidades(List<CidadeResponseV3> cidades) {
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
