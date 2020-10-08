package com.food.service;

import com.food.api.model.response.FotoProdutoResponse;
import com.food.api.model.response.FotoStreamResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FotoProdutoService {
    FotoProdutoResponse salvar(Long restauranteId, Long produtoId, String descricao, MultipartFile arquivo);

    FotoProdutoResponse buscar(Long restauranteId, Long produtoId);

    FotoStreamResponse buscarArquivoFoto(Long restauranteId, Long produtoId);

    void excluir(Long restauranteId, Long produtoId);
}
