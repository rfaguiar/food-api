package com.food.api.v1.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UsuarioSemSenhaRequest(@Schema(example = "João da Silva", required = true)
                                     @JsonProperty("nome")
                                     @NotBlank
                                     String nome,
                                     @Schema(example = "joao.ger@algafood.com.br", required = true)
                                     @JsonProperty("email")
                                     @NotBlank
                                     @Email
                                     String email) {
}
