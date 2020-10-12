package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.PedidoResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumoModel")
public class PedidosResumoModelOpenApi {

    private PedidosResumoModelOpenApi.PedidoResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    public class PedidoResumoEmbeddedModelOpenApi {

        private List<PedidoResponse> pedidos;

        public List<PedidoResponse> getPedidos() {
            return pedidos;
        }

        public void setPedidos(List<PedidoResponse> pedidos) {
            this.pedidos = pedidos;
        }
    }

    public PedidoResumoEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(PedidoResumoEmbeddedModelOpenApi _embedded) {
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
