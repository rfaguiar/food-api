package com.food.api.v2.openapi.model;

import com.food.api.v2.model.response.CozinhaResponseV2;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(defaultValue = "CozinhasModel")
public class CozinhasModelV2OpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelV2OpenApi page;

    @Schema(defaultValue = "CozinhasEmbeddedModel")
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaResponseV2> cozinhas;

        public List<CozinhaResponseV2> getCozinhas() {
            return cozinhas;
        }

        public void setCozinhas(List<CozinhaResponseV2> cozinhas) {
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

    public PageModelV2OpenApi getPage() {
        return page;
    }

    public void setPage(PageModelV2OpenApi page) {
        this.page = page;
    }
}
