package com.food.infrastructure.service;

import com.food.api.model.request.ProdutoRequest;
import com.food.api.model.response.ProdutoResponse;
import com.food.api.model.response.RestauranteResponse;
import com.food.domain.exception.ProdutoNaoEncontradaException;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.ProdutoRepository;
import com.food.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public ProdutoResponse buscarPorId(Long restauranteId, Long produtoId) {
        restauranteService.buscarPorId(restauranteId);
        Produto produto = buscarPorIdEValidar(restauranteId, produtoId);
        return new ProdutoResponse(produto);
    }

    @Override
    public List<ProdutoResponse> listarProdutosPorId(Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorIdEValidar(restauranteId);
        return produtoRepository.findAtivosByRestaurante(restaurante)
                .stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoResponse> listarProdutosPorIdEAtivos(Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorIdEValidar(restauranteId);
        return produtoRepository.findByRestaurante(restaurante)
                .stream()
                .map(ProdutoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoResponse adicionar(Long restauranteId, ProdutoRequest dto) {
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = new Produto(null, dto.nome(), dto.descricao(), dto.preco(), dto.ativo(), restaurante);
        Produto novo = produtoRepository.save(produto);
        return new ProdutoResponse(novo);
    }

    @Override
    public ProdutoResponse atualizar(Long restauranteId, Long produtoId, ProdutoRequest dto) {
        Produto antigo = buscarPorIdEValidar(restauranteId, produtoId);
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = new Produto(antigo.id(), dto.nome(), dto.descricao(), dto.preco(), dto.ativo(), restaurante);
        Produto novo = produtoRepository.save(produto);
        return new ProdutoResponse(novo);
    }

    Produto buscarPorIdEValidar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradaException(produtoId));
    }
}
