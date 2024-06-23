package com.food.api.v1.openapi.controller;

import com.food.api.v1.model.response.PermissaoResponse;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

import static com.food.config.OpenApiConfig.TAG_PERMISSOES;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_PERMISSOES)
public interface PermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões")
    CollectionModel<PermissaoResponse> listar();
}
