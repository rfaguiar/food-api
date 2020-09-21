package com.food.infrastructure.service;

import com.food.api.model.request.EnderecoRequest;
import com.food.api.model.request.ItemPedidoRequest;
import com.food.api.model.request.PedidoRequest;
import com.food.api.model.response.PedidoResponse;
import com.food.api.model.response.PedidoResumoResponse;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.PedidoNaoEncontradoException;
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
import com.food.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, RestauranteServiceImpl cadastroRestaurante,
                             CidadeServiceImpl cadastroCidade, UsuarioServiceImpl cadastroUsuario,
                             ProdutoServiceImpl cadastroProduto, FormaPagamentoServiceImpl cadastroFormaPagamento, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.cadastroRestaurante = cadastroRestaurante;
        this.cadastroCidade = cadastroCidade;
        this.cadastroUsuario = cadastroUsuario;
        this.cadastroProduto = cadastroProduto;
        this.cadastroFormaPagamento = cadastroFormaPagamento;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public List<PedidoResumoResponse> buscarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResumoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponse buscar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        return new PedidoResponse(pedido);
    }

    @Override
    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido = pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido = pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = buscarOuFalhar(pedidoId);
        pedido = pedido.entregar();
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse emitirPedido(PedidoRequest pedidoRequest) {
        try {
            Pedido pedido = validarPedido(pedidoRequest);
            pedido = validarProdutosDoPedido(pedido);
            pedido = pedido.calcularValorTotal(pedido);
            Pedido finalPedido = pedidoRepository.save(pedido);
            Set<ItemPedido> itens = pedido.itens().stream().map(item -> new ItemPedido(item.id(),
                    item.precoUnitario(),
                    item.precoTotal(),
                    item.quantidade(),
                    item.observacao(),
                    finalPedido,
                    item.produto())).collect(Collectors.toSet());
            itemPedidoRepository.saveAll(itens);
            return new PedidoResponse(finalPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private Pedido validarProdutosDoPedido(Pedido pedido) {
        Set<ItemPedido> itens = pedido.itens().stream().map(item -> {
            Produto produto = cadastroProduto.buscarPorIdEValidar(pedido.restaurante().id(), item.produto().id());
            return new ItemPedido(item.id(),
                    produto.preco(),
                    item.precoTotal(),
                    item.quantidade(),
                    item.observacao(),
                    item.pedido(),
                    produto);
        }).collect(Collectors.toSet());

        return new Pedido(pedido.id(), pedido.subtotal(), pedido.taxaFrete(), pedido.valorTotal(),
                pedido.enderecoEntrega(), StatusPedido.CRIADO, pedido.dataCriacao(), pedido.dataConfirmacao(),
                pedido.dataCancelamento(),pedido.dataEntrega(), pedido.formaPagamento(), pedido.restaurante(),
                pedido.cliente(), itens);
    }

    private Pedido validarPedido(PedidoRequest pedidoRequest) {
        // TODO pegar usuário autenticado
        Usuario cliente = cadastroUsuario.buscarEValidarPorId(1L);
        Cidade cidade = cadastroCidade.buscarPorIdEValidar(pedidoRequest.enderecoEntrega().cidade().id());
        Restaurante restaurante = cadastroRestaurante.buscarPorIdEValidar(pedidoRequest.restaurante().id());
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarPorIdEValidar(pedidoRequest.formaPagamento().id());
        validarFormaPagamento(restaurante, formaPagamento);

        EnderecoRequest enderecoRequest = pedidoRequest.enderecoEntrega();
        Endereco endereco = new Endereco(enderecoRequest.cep(), enderecoRequest.logradouro(), enderecoRequest.numero(), enderecoRequest.complemento(), enderecoRequest.bairro(), cidade);
        Set<ItemPedido> itens = pedidoRequest.itens().stream().map(this::criarItemPedido).collect(Collectors.toSet());
        return new Pedido(null, null, restaurante.taxaFrete(), null,
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

    private Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
