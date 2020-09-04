package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public record Endereco(@Column(name = "endereco_cep")
                       String cep,
                       @Column(name = "endereco_logradouro")
                       String logradouro,
                       @Column(name = "endereco_numero")
                       String numero,
                       @Column(name = "endereco_complemento")
                       String complemento,
                       @Column(name = "endereco_bairro")
                       String bairro,
                       @ManyToOne
                       @JoinColumn(name = "endereco_cidade_id")
                       Cidade cidade) {
    public Endereco() {
        this(null, null, null, null, null, null);
    }
}
