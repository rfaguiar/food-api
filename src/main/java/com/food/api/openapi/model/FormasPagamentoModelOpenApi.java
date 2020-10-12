package com.food.api.openapi.model;

import com.food.api.model.response.FormaPagamentoResponse;
import io.swagger.annotations.ApiModel;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormaPagamentosModel")
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoModelOpenApi.FormaPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("FormaPagamentosEmbeddedModel")
    public class FormaPagamentoEmbeddedModelOpenApi {

        private List<FormaPagamentoResponse> formasPagamento;

        public List<FormaPagamentoResponse> getFormasPagamento() {
            return formasPagamento;
        }

        public void setFormasPagamento(List<FormaPagamentoResponse> formasPagamento) {
            this.formasPagamento = formasPagamento;
        }
    }

    public FormaPagamentoEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(FormaPagamentoEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
