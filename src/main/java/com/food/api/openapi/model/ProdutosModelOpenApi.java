package com.food.api.openapi.model;

import com.food.api.model.response.ProdutoResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProdutosModel")
public class ProdutosModelOpenApi {

    private ProdutosModelOpenApi.ProdutoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("ProdutosEmbeddedModel")
    public class ProdutoEmbeddedModelOpenApi {

        private List<ProdutoResponse> produtos;

        public List<ProdutoResponse> getProdutos() {
            return produtos;
        }

        public void setProdutos(List<ProdutoResponse> produtos) {
            this.produtos = produtos;
        }
    }

    public ProdutoEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(ProdutoEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
