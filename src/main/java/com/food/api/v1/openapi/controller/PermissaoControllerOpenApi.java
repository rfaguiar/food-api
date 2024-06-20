package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.response.PermissaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

import static com.food.config.OpenApiConfig.TAG_PERMISSOES;

@Tag(name = TAG_PERMISSOES)
public interface PermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões")
    CollectionModel<PermissaoResponse> listar();
}
