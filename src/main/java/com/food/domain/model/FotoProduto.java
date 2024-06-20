package com.food.domain.model;

import javax.persistence.*;

@Entity
public record FotoProduto(@Id
                          @Column(name = "produto_id")
                          Long id,
                          @OneToOne(fetch = FetchType.LAZY)
                          @MapsId
                          Produto produto,
                          String nomeArquivo,
                          String descricao,
                          String contentType,
                          Long tamanho) {

    public FotoProduto() {
        this(null, null, null, null, null, null);
    }
}
