package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public record FotoProdutoResponse(@ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
                                  @JsonProperty("nomeArquivo") String nomeArquivo,
                                  @ApiModelProperty(example = "Prime Rib ao ponto")
                                  @JsonProperty("descricao") String descricao,
                                  @ApiModelProperty(example = "image/jpeg")
                                  @JsonProperty("contentType") String contentType,
                                  @ApiModelProperty(example = "202912")
                                  @JsonProperty("tamanho") Long tamanho) {
}
