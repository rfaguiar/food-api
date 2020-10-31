package com.food.infrastructure.service;

import com.food.api.v1.model.request.ProdutoRequest;
import com.food.domain.exception.ProdutoNaoEncontradaException;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.ProdutoRepository;
import com.food.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteServiceImpl restauranteService;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository, RestauranteServiceImpl restauranteService) {
        this.produtoRepository = produtoRepository;
        this.restauranteService = restauranteService;
    }

    @Override
    public Produto buscarPorId(Long restauranteId, Long produtoId) {
        restauranteService.buscarPorId(restauranteId);
        return buscarPorIdEValidar(restauranteId, produtoId);
    }

    @Override
    public List<Produto> listarProdutosPorId(Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorIdEValidar(restauranteId);
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    @Override
    public List<Produto> listarProdutosPorIdEAtivos(Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorIdEValidar(restauranteId);
        return produtoRepository.findByRestaurante(restaurante);
    }

    @Override
    public Produto adicionar(Long restauranteId, ProdutoRequest dto) {
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = new Produto(null, dto.nome(), dto.descricao(), dto.preco(), dto.ativo(), restaurante);
        return produtoRepository.save(produto);
    }

    @Override
    public Produto atualizar(Long restauranteId, Long produtoId, ProdutoRequest dto) {
        Produto antigo = buscarPorIdEValidar(restauranteId, produtoId);
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = new Produto(antigo.getId(), dto.nome(), dto.descricao(), dto.preco(), dto.ativo(), restaurante);
        return produtoRepository.save(produto);
    }

    Produto buscarPorIdEValidar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradaException(produtoId));
    }
}
