package com.food.infrastructure.service;

import com.food.api.model.request.ProdutoRequest;
import com.food.api.model.response.ProdutoResponse;
import com.food.api.model.response.RestauranteResponse;
import com.food.domain.exception.ProdutoNaoEncontradaException;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.ProdutoRepository;
import com.food.service.ProdutoService;
import com.food.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteService restauranteService;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository, RestauranteService restauranteService) {
        this.produtoRepository = produtoRepository;
        this.restauranteService = restauranteService;
    }

    @Override
    public ProdutoResponse buscarPorId(Long restauranteId, Long produtoId) {
        restauranteService.buscarPorId(restauranteId);
        Produto produto = buscarPorIdEValidar(restauranteId, produtoId);
        return new ProdutoResponse(produto);
    }

    @Override
    public List<ProdutoResponse> listarProdutosPorId(Long restauranteId) {
        RestauranteResponse restauranteResponse = restauranteService.buscarPorId(restauranteId);
        List<Produto> produtos = produtoRepository.findByRestaurante(new Restaurante(restauranteResponse.id(), null, null, null, null, Boolean.TRUE,
                null, null, null, null));
        return produtos.stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoResponse adicionar(Long restauranteId, ProdutoRequest dto) {
        RestauranteResponse restauranteResponse = restauranteService.buscarPorId(restauranteId);
        Produto produto = new Produto(null, dto.nome(), dto.descricao(), dto.preco(), dto.ativo(),
                new Restaurante(restauranteResponse.id(), null, null, null, null, null,
                        null, null, null, null));
        Produto novo = produtoRepository.save(produto);
        return new ProdutoResponse(novo);
    }

    @Override
    public ProdutoResponse atualizar(Long restauranteId, Long produtoId, ProdutoRequest dto) {
        Produto antigo = buscarPorIdEValidar(restauranteId, produtoId);
        RestauranteResponse restauranteResponse = restauranteService.buscarPorId(restauranteId);
        Produto produto = new Produto(antigo.id(), dto.nome(), dto.descricao(), dto.preco(), dto.ativo(),
                new Restaurante(restauranteResponse.id(), null, null, null, null, null,
                        null, null, null, null));
        Produto novo = produtoRepository.save(produto);
        return new ProdutoResponse(novo);
    }

    private Produto buscarPorIdEValidar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradaException(produtoId));
    }
}
