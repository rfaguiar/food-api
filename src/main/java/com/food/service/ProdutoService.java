package com.food.service;

import com.food.api.v1.model.request.ProdutoRequest;
import com.food.domain.model.Produto;

import java.util.List;

public interface ProdutoService {
    Produto buscarPorId(Long restauranteId, Long produtoId);

    List<Produto> listarProdutosPorId(Long restauranteId);

    Produto adicionar(Long restauranteId, ProdutoRequest produto);

    Produto atualizar(Long restauranteId, Long produtoId, ProdutoRequest produto);

    List<Produto> listarProdutosPorIdEAtivos(Long restauranteId);
}
