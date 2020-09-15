package com.food.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UsuarioSemSenhaRequest(@JsonProperty("nome")
                                     @NotBlank
                                     String nome,
                                     @JsonProperty("email")
                                     @NotBlank
                                     @Email
                                     String email) {
}
