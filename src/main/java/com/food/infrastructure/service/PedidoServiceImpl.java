package com.food.infrastructure.service;

import com.food.api.v1.model.request.EnderecoRequest;
import com.food.api.v1.model.request.ItemPedidoRequest;
import com.food.api.v1.model.request.PedidoRequest;
import com.food.config.FoodSecurity;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.PedidoNaoEncontradoException;
import com.food.domain.filter.PedidoFilter;
import com.food.domain.model.Cidade;
import com.food.domain.model.Endereco;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.ItemPedido;
import com.food.domain.model.Pedido;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.model.StatusPedido;
import com.food.domain.model.Usuario;
import com.food.domain.repository.ItemPedidoRepository;
import com.food.domain.repository.PedidoRepository;
import com.food.infrastructure.repository.spec.PedidoSpecs;
import com.food.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RestauranteServiceImpl cadastroRestaurante;
    private final CidadeServiceImpl cadastroCidade;
    private final UsuarioServiceImpl cadastroUsuario;
    private final ProdutoServiceImpl cadastroProduto;
    private final FormaPagamentoServiceImpl cadastroFormaPagamento;
    private final ItemPedidoRepository itemPedidoRepository;
    private final FoodSecurity foodSecutiry;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, RestauranteServiceImpl cadastroRestaurante,
                             CidadeServiceImpl cadastroCidade, UsuarioServiceImpl cadastroUsuario,
                             ProdutoServiceImpl cadastroProduto, FormaPagamentoServiceImpl cadastroFormaPagamento, ItemPedidoRepository itemPedidoRepository, FoodSecurity foodSecutiry) {
        this.pedidoRepository = pedidoRepository;
        this.cadastroRestaurante = cadastroRestaurante;
        this.cadastroCidade = cadastroCidade;
        this.cadastroUsuario = cadastroUsuario;
        this.cadastroProduto = cadastroProduto;
        this.cadastroFormaPagamento = cadastroFormaPagamento;
        this.itemPedidoRepository = itemPedidoRepository;
        this.foodSecutiry = foodSecutiry;
    }

    @Override
    public Page<Pedido> buscarTodos(PedidoFilter filtro, Pageable pageable) {
        return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
    }

    @Override
    public Pedido buscar(String codigoPedido) {
        return buscarOuFalhar(codigoPedido);
    }

    @Override
    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = buscarOuFalhar(codigoPedido);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = buscarOuFalhar(codigoPedido);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = buscarOuFalhar(codigoPedido);
        pedido = pedido.entregar();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido emitirPedido(PedidoRequest pedidoRequest) {
        try {
            Pedido pedido = validarPedido(pedidoRequest);
            pedido = validarProdutosDoPedido(pedido);
            pedido = pedido.calcularValorTotal();
            Pedido finalPedido = pedidoRepository.save(pedido);
            Set<ItemPedido> itens = pedido.getItens().stream().map(item -> new ItemPedido(item.id(),
                    item.precoUnitario(),
                    item.precoTotal(),
                    item.quantidade(),
                    item.observacao(),
                    finalPedido,
                    item.produto())).collect(Collectors.toSet());
            itemPedidoRepository.saveAll(itens);
            return finalPedido;
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private Pedido validarProdutosDoPedido(Pedido pedido) {
        Set<ItemPedido> itens = pedido.getItens().stream().map(item -> {
            Produto produto = cadastroProduto.buscarPorIdEValidar(pedido.getRestaurante().id(), item.produto().id());
            return new ItemPedido(item.id(),
                    produto.preco(),
                    item.precoTotal(),
                    item.quantidade(),
                    item.observacao(),
                    item.pedido(),
                    produto);
        }).collect(Collectors.toSet());
        pedido.setItens(itens);
        pedido.setStatus(StatusPedido.CRIADO);
        return pedido;
    }

    private Pedido validarPedido(PedidoRequest pedidoRequest) {
        Usuario cliente = cadastroUsuario.buscarEValidarPorId(foodSecutiry.getUsuarioId());
        Cidade cidade = cadastroCidade.buscarPorIdEValidar(pedidoRequest.enderecoEntrega().cidade().id());
        Restaurante restaurante = cadastroRestaurante.buscarPorIdEValidar(pedidoRequest.restaurante().id());
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarPorIdEValidar(pedidoRequest.formaPagamento().id());
        validarFormaPagamento(restaurante, formaPagamento);

        EnderecoRequest enderecoRequest = pedidoRequest.enderecoEntrega();
        Endereco endereco = new Endereco(enderecoRequest.cep(), enderecoRequest.logradouro(), enderecoRequest.numero(), enderecoRequest.complemento(), enderecoRequest.bairro(), cidade);
        Set<ItemPedido> itens = pedidoRequest.itens().stream().map(this::criarItemPedido).collect(Collectors.toSet());
        return new Pedido(null, UUID.randomUUID().toString(), null, restaurante.taxaFrete(), null,
                endereco,
                StatusPedido.CRIADO, null, null, null,
                null, formaPagamento, restaurante, cliente, itens);
    }

    private ItemPedido criarItemPedido(ItemPedidoRequest item) {
        Produto produto = new Produto(item.produtoId(), null, null, null, null, null);
        return new ItemPedido(null, null, null,
                item.quantidade(),
                item.observacao(),
                null,
                produto);
    }

    private void validarFormaPagamento(Restaurante restaurante, FormaPagamento formaPagamento) {
        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.descricao()));
        }
    }

    private Pedido buscarOuFalhar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }
}
