package com.food.api.v1.openapi.model;

import com.food.api.v1.model.response.RestauranteBasicoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(defaultValue = "RestaurantesBasicoModel")
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesBasicoModelOpenApi.RestauranteEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(defaultValue = "RestaurantesEmbeddedModel")
    public class RestauranteEmbeddedModelOpenApi {

        private List<RestauranteBasicoResponse> restaurantes;

        public List<RestauranteBasicoResponse> getRestaurantes() {
            return restaurantes;
        }

        public void setRestaurantes(List<RestauranteBasicoResponse> restaurantes) {
            this.restaurantes = restaurantes;
        }
    }

    public RestauranteEmbeddedModelOpenApi get_embedded() {
        return _embedded;
    }

    public void set_embedded(RestauranteEmbeddedModelOpenApi _embedded) {
        this._embedded = _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
