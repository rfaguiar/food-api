package com.food.api.v3.openapi.model;

import com.food.api.v3.model.response.CozinhaResponseV3;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
public class CozinhasModelV3OpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelV3OpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaResponseV3> cozinhas;

        public List<CozinhaResponseV3> getCozinhas() {
            return cozinhas;
        }

        public void setCozinhas(List<CozinhaResponseV3> cozinhas) {
            this.cozinhas = cozinhas;
        }
    }

    public CozinhasEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(CozinhasEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public PageModelV3OpenApi getPage() {
        return page;
    }

    public void setPage(PageModelV3OpenApi page) {
        this.page = page;
    }
}
