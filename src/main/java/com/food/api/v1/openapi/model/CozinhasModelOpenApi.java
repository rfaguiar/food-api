package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.CozinhaResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi {

    private CozinhasModelOpenApi.CozinhaEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    public class CozinhaEmbeddedModelOpenApi {

        private List<CozinhaResponse> cozinhas;

        public List<CozinhaResponse> getCozinhas() {
            return cozinhas;
        }

        public void setCozinhas(List<CozinhaResponse> cozinhas) {
            this.cozinhas = cozinhas;
        }
    }

    public CozinhaEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(CozinhaEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public PageModelOpenApi getPage() {
        return page;
    }

    public void setPage(PageModelOpenApi page) {
        this.page = page;
    }
}
