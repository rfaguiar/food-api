package com.food.service;

import com.food.api.model.request.FotoProdutoRequest;
import com.food.api.model.response.FotoProdutoResponse;
import com.food.api.model.response.FotoStreamResponse;

public interface FotoProdutoService {
    FotoProdutoResponse salvar(Long restauranteId, Long produtoId, FotoProdutoRequest fotoProdutoRequest);

    FotoProdutoResponse buscar(Long restauranteId, Long produtoId);

    FotoStreamResponse buscarArquivoFoto(Long restauranteId, Long produtoId);

    void excluir(Long restauranteId, Long produtoId);
}
