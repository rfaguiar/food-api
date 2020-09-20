package com.food.infrastructure.service;

import com.food.api.model.response.PedidoResponse;
import com.food.domain.exception.PedidoNaoEncontradoException;
import com.food.domain.model.Pedido;
import com.food.domain.repository.PedidoRepository;
import com.food.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<PedidoResponse> buscarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponse buscar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        return new PedidoResponse(pedido);
    }

    private Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
