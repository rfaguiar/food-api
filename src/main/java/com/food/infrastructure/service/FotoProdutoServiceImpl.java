package com.food.infrastructure.service;

import com.food.api.model.request.FotoProdutoRequest;
import com.food.api.model.response.FotoProdutoResponse;
import com.food.domain.model.FotoProduto;
import com.food.domain.model.Produto;
import com.food.domain.repository.ProdutoProdutoRepository;
import com.food.service.FotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FotoProdutoServiceImpl implements FotoProdutoService {

    private final ProdutoProdutoRepository produtoProdutoRepository;
    private final ProdutoServiceImpl produtoService;

    @Autowired
    public FotoProdutoServiceImpl(ProdutoProdutoRepository produtoProdutoRepository, ProdutoServiceImpl produtoService) {
        this.produtoProdutoRepository = produtoProdutoRepository;
        this.produtoService = produtoService;
    }

    @Override
    @Transactional
    public FotoProdutoResponse salvar(Long restauranteId, Long produtoId, FotoProdutoRequest fotoProdutoRequest) {
        Produto produto = produtoService.buscarPorIdEValidar(restauranteId, produtoId);
        var fotoProduto = new FotoProduto(
                null,
                produto,
                fotoProdutoRequest.getArquivo().getOriginalFilename(),
                fotoProdutoRequest.getDescricao(),
                fotoProdutoRequest.getArquivo().getContentType(),
                fotoProdutoRequest.getArquivo().getSize());
        fotoProduto = produtoProdutoRepository.save(fotoProduto);
        return new FotoProdutoResponse(fotoProduto.nomeArquivo(), fotoProduto.descricao(), fotoProduto.contentType(), fotoProduto.tamanho());
    }
}
