package com.food.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

//veja RFC 7807
@Schema(name = Problem.PROBLEMA)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problem(@Schema(example = "400")
                      @JsonProperty("status") Integer status,
                      @Schema(example = "https://food.com.br/recurso-nao-encontrado")
                      @JsonProperty("type") String type,
                      @Schema(example = "Recurso não encontrado")
                      @JsonProperty("title") String title,
                      @Schema(example = "Não existe um cadastro de restaurante com código 10")
                      @JsonProperty("detail") String detail,
                      @Schema(example = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.")
                      @JsonProperty("userMessage") String userMessage,
                      @Schema(example = "2020-10-04T23:22:42.897164")
                      @JsonProperty("timestamp") LocalDateTime timestamp,
                      @Schema(example = "Objetos ou campos que geraram o erro (opcional)")
                      @JsonProperty("fields") List<Field> fields) {
    public Problem(Integer status,
                   String type,
                   String title,
                   String detail,
                   String userMessage) {
        this(status, type, title, detail, userMessage, LocalDateTime.now(), null);
    }
    public static final String PROBLEMA = "Problema";

    @Schema(name = "ObjetoProblema")
    public record Object(
            @Schema(example = "preco")
            String name,
            @Schema(example = "O preço é inválido")
            String userMessage
    ) {}
}
