package com.food.api.v1.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.domain.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoResponse {
    @Schema(example = "38400-000")
    private String cep;
    @Schema(example = "Rua Floriano Peixoto")
    private String logradouro;
    @Schema(example = "\"1500\"")
    private String numero;
    @Schema(example = "Apto 901")
    private String complemento;
    @Schema(example = "Centro")
    private String bairro;
    private CidadeResumoResponse cidade;

    public EnderecoResponse(Endereco endereco) {
        this(endereco.getCep(), endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(),
                new CidadeResumoResponse(endereco.getCidade()));
    }

    public EnderecoResponse(Optional<Endereco> enderecoOpt) {
        this(enderecoOpt.map(Endereco::getCep).orElse(null),
                enderecoOpt.map(Endereco::getLogradouro).orElse(null),
                enderecoOpt.map(Endereco::getNumero).orElse(null),
                enderecoOpt.map(Endereco::getComplemento).orElse(null),
                enderecoOpt.map(Endereco::getBairro).orElse(null),
                enderecoOpt.map(e -> new CidadeResumoResponse(Optional.ofNullable(e.getCidade())))
                        .orElse(new CidadeResumoResponse(Optional.empty())));
    }

    public EnderecoResponse(String cep, String logradouro, String numero, String complemento, String bairro, CidadeResumoResponse cidade) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public CidadeResumoResponse getCidade() {
        return cidade;
    }
}
