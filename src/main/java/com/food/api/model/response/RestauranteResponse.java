package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.domain.model.Endereco;
import com.food.domain.model.Restaurante;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestauranteResponse(@ApiModelProperty(example = "1")
                                  @JsonProperty("id")
                                  Long id,
                                  @ApiModelProperty(example = "Thai Gourmet")
                                  @JsonProperty("nome")
                                  String nome,
                                  @ApiModelProperty(example = "12.00")
                                  @JsonProperty("taxaFrete")
                                  BigDecimal taxaFrete,
                                  @JsonProperty("ativo")
                                  Boolean ativo,
                                  @JsonProperty("aberto")
                                  Boolean aberto,
                                  @JsonProperty("endereco")
                                  EnderecoResponse endereco,
                                  @JsonProperty("cozinha")
                                  CozinhaResponse cozinha) {
    
    public RestauranteResponse(Restaurante restaurante) {
        this(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(), restaurante.ativo(), restaurante.aberto(),
                Optional.ofNullable(restaurante.endereco()).map(EnderecoResponse::new).orElse(null),
                new CozinhaResponse(restaurante.cozinha()));
    }
}
