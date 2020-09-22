package com.food.service;

import com.food.api.model.request.ProdutoRequest;
import com.food.api.model.response.ProdutoResponse;

import java.util.List;

public interface ProdutoService {
    ProdutoResponse buscarPorId(Long restauranteId, Long produtoId);

    List<ProdutoResponse> listarProdutosPorId(Long restauranteId);

    ProdutoResponse adicionar(Long restauranteId, ProdutoRequest produto);

    ProdutoResponse atualizar(Long restauranteId, Long produtoId, ProdutoRequest produto);

    List<ProdutoResponse> listarProdutosPorIdEAtivos(Long restauranteId);
}
