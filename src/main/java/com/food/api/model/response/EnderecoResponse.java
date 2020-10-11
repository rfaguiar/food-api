package com.food.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.domain.model.Endereco;
import io.swagger.annotations.ApiModelProperty;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoResponse {
    @ApiModelProperty(example = "38400-000")
    private String cep;
    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;
    @ApiModelProperty(example = "\"1500\"")
    private String numero;
    @ApiModelProperty(example = "Apto 901")
    private String complemento;
    @ApiModelProperty(example = "Centro")
    private String bairro;
    private CidadeResumoResponse cidade;

    public EnderecoResponse(Endereco endereco) {
        this(endereco.cep(), endereco.logradouro(), endereco.numero(), endereco.complemento(), endereco.bairro(),
                new CidadeResumoResponse(endereco.cidade()));
    }

    public EnderecoResponse(Optional<Endereco> enderecoOpt) {
        this(enderecoOpt.map(Endereco::cep).orElse(null),
                enderecoOpt.map(Endereco::logradouro).orElse(null),
                enderecoOpt.map(Endereco::numero).orElse(null),
                enderecoOpt.map(Endereco::complemento).orElse(null),
                enderecoOpt.map(Endereco::bairro).orElse(null),
                enderecoOpt.map(e -> new CidadeResumoResponse(Optional.ofNullable(e.cidade())))
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
