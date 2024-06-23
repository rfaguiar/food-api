package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.FormaPagamentoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(defaultValue = "FormaPagamentosModel")
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoModelOpenApi.FormaPagamentoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(defaultValue = "FormaPagamentosEmbeddedModel")
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
