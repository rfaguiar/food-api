package com.food.infrastructure.service;

import com.food.api.model.request.FotoProdutoRequest;
import com.food.api.model.response.FotoProdutoResponse;
import com.food.domain.exception.FotoProdutoNaoEncontradaException;
import com.food.domain.model.FotoProduto;
import com.food.domain.model.Produto;
import com.food.domain.repository.FotoProdutoRepository;
import com.food.service.FotoProdutoService;
import com.food.service.FotoStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FotoProdutoServiceImpl implements FotoProdutoService {

    private static final Logger LOGGER = LogManager.getLogger(FotoProdutoServiceImpl.class);

    private final FotoProdutoRepository fotoProdutoRepository;
    private final ProdutoServiceImpl produtoService;
    private final FotoStorageService fotoStorageService;

    @Autowired
    public FotoProdutoServiceImpl(FotoProdutoRepository fotoProdutoRepository, ProdutoServiceImpl produtoService, FotoStorageService fotoStorageService) {
        this.fotoProdutoRepository = fotoProdutoRepository;
        this.produtoService = produtoService;
        this.fotoStorageService = fotoStorageService;
    }

    @Override
    @Transactional
    public FotoProdutoResponse salvar(Long restauranteId, Long produtoId, FotoProdutoRequest fotoProdutoRequest) {
        Produto produto = produtoService.buscarPorIdEValidar(restauranteId, produtoId);
        fotoProdutoRepository.findFotoById(restauranteId, produtoId)
                .ifPresent(this::removerArquivoExistente);
        String fileName = fotoProdutoRequest.getArquivo().getOriginalFilename();
        String nomeArquivo = UUID.randomUUID().toString() + "_" + fileName;
        var fotoProduto = new FotoProduto(
                null,
                produto,
                nomeArquivo,
                fotoProdutoRequest.getDescricao(),
                fotoProdutoRequest.getArquivo().getContentType(),
                fotoProdutoRequest.getArquivo().getSize());
        fotoProduto = fotoProdutoRepository.save(fotoProduto);

        try (InputStream fotoInputStream = fotoProdutoRequest.getArquivo().getInputStream()) {
            fotoStorageService.armazenar(new FotoStorageService.NovaFoto(nomeArquivo, fotoInputStream));
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return new FotoProdutoResponse(fotoProduto.nomeArquivo(), fotoProduto.descricao(), fotoProduto.contentType(), fotoProduto.tamanho());
    }

    @Override
    public FotoProdutoResponse buscar(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        return new FotoProdutoResponse(fotoProduto.nomeArquivo(),
                fotoProduto.descricao(),
                fotoProduto.contentType(),
                fotoProduto.tamanho());
    }

    @Override
    public InputStream buscarArquivoFoto(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        return fotoStorageService.recuperar(fotoProduto.nomeArquivo());
    }

    private FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return fotoProdutoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    private void removerArquivoExistente(FotoProduto fotoProduto) {
        fotoProdutoRepository.delete(fotoProduto);
        fotoStorageService.remover(fotoProduto.nomeArquivo());
    }
}
