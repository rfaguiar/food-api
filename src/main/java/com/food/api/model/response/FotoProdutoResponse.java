package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FotoProdutoResponse(@JsonProperty("nomeArquivo") String nomeArquivo,
                                  @JsonProperty("descricao") String descricao,
                                  @JsonProperty("contentType") String contentType,
                                  @JsonProperty("tamanho") Long tamanho) {
}
