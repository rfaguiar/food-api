package com.food.api.v1.controller;

import com.food.api.v1.assembler.PedidoResponseAssembler;
import com.food.api.v1.assembler.PedidoResumoResponseAssembler;
import com.food.api.v1.model.request.PedidoRequest;
import com.food.api.v1.model.response.PedidoResponse;
import com.food.api.v1.model.response.PedidoResumoResponse;
import com.food.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.food.domain.filter.PedidoFilter;
import com.food.domain.model.Pedido;
import com.food.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoService pedidoService;
    private final PedidoResponseAssembler pedidoResponseAssembler;
    private final PedidoResumoResponseAssembler pedidoResumoResponseAssembler;
    private final PagedResourcesAssembler<Pedido> pedidoPagedResourcesAssembler;

    @Autowired
    public PedidoController(PedidoService pedidoService, PedidoResponseAssembler pedidoResponseAssembler, PedidoResumoResponseAssembler pedidoResumoResponseAssembler, PagedResourcesAssembler<Pedido> pedidoPagedResourcesAssembler) {
        this.pedidoService = pedidoService;
        this.pedidoResponseAssembler = pedidoResponseAssembler;
        this.pedidoResumoResponseAssembler = pedidoResumoResponseAssembler;
        this.pedidoPagedResourcesAssembler = pedidoPagedResourcesAssembler;
    }

    @Override
    @GetMapping
    public PagedModel<PedidoResumoResponse> pesquisar(PedidoFilter filtro,
                                                      @PageableDefault(size = 2) Pageable pageable) {
        Page<Pedido> pedidoPage = pedidoService.buscarTodos(filtro, pageable);
        return pedidoPagedResourcesAssembler.toModel(pedidoPage, pedidoResumoResponseAssembler);
    }

    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoResponse porId(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        return pedidoResponseAssembler.toModel(pedido);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse adicionar(@RequestBody @Valid PedidoRequest pedidoRequest) {
        return pedidoResponseAssembler.toModel(pedidoService.emitirPedido(pedidoRequest));
    }

    @Override
    @PutMapping("/{codigoPedido}/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido){
        pedidoService.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{codigoPedido}/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        pedidoService.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{codigoPedido}/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        pedidoService.entregar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

}
