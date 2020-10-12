package com.food.service;

import com.food.api.model.response.FotoStreamResponse;
import com.food.domain.model.FotoProduto;
import org.springframework.web.multipart.MultipartFile;

public interface FotoProdutoService {
    FotoProduto salvar(Long restauranteId, Long produtoId, String descricao, MultipartFile arquivo);

    FotoProduto buscar(Long restauranteId, Long produtoId);

    FotoStreamResponse buscarArquivoFoto(Long restauranteId, Long produtoId);

    void excluir(Long restauranteId, Long produtoId);
}
