package com.food.infrastructure.service;

import com.food.api.model.response.FotoStreamResponse;
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
import org.springframework.web.multipart.MultipartFile;

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
    public FotoProduto salvar(Long restauranteId, Long produtoId, String descricao, MultipartFile arquivo) {
        Produto produto = produtoService.buscarPorIdEValidar(restauranteId, produtoId);
        fotoProdutoRepository.findFotoById(restauranteId, produtoId)
                .ifPresent(this::removerArquivoExistente);
        String fileName = arquivo.getOriginalFilename();
        String nomeArquivo = UUID.randomUUID().toString() + "_" + fileName;
        var fotoProduto = new FotoProduto(
                null,
                produto,
                nomeArquivo,
                descricao,
                arquivo.getContentType(),
                arquivo.getSize());
        fotoProduto = fotoProdutoRepository.save(fotoProduto);

        try (InputStream fotoInputStream = arquivo.getInputStream()) {
            fotoStorageService.armazenar(new FotoStorageService.NovaFoto(nomeArquivo,
                    arquivo.getContentType(),
                    fotoInputStream));
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return fotoProduto;
    }

    @Override
    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return buscarOuFalhar(restauranteId, produtoId);
    }

    @Override
    public FotoStreamResponse buscarArquivoFoto(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.nomeArquivo());
        return new FotoStreamResponse(fotoProduto.contentType(), fotoRecuperada);
    }

    @Override
    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        fotoProdutoRepository.delete(fotoProduto);
        fotoProdutoRepository.flush();
        fotoStorageService.remover(fotoProduto.nomeArquivo());

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
