package com.food.api.assembler;

import com.food.api.controller.CidadeController;
import com.food.api.controller.FormaPagamentoController;
import com.food.api.controller.PedidoController;
import com.food.api.controller.RestauranteController;
import com.food.api.controller.RestauranteProdutoController;
import com.food.api.controller.UsuarioController;
import com.food.api.model.response.PedidoResponse;
import com.food.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResponseAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public PedidoResponseAssembler(FoodLinks foodLinks) {
        super(PedidoController.class, PedidoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public PedidoResponse toModel(Pedido pedido) {
        PedidoResponse pedidoResponse = new PedidoResponse(pedido);

        pedidoResponse.add(linkTo(
                methodOn(PedidoController.class).porId(pedido.getCodigo())
        ).withSelfRel());

        pedidoResponse.add(foodLinks.linkToPedidos());

        pedidoResponse.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .porId(pedido.getRestaurante().id())).withSelfRel());

        pedidoResponse.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .porId(pedido.getCliente().id())).withSelfRel());

        pedidoResponse.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .porId(pedido.getFormaPagamento().id())).withSelfRel());

        pedidoResponse.getEnderecoEntrega().cidade().add(linkTo(methodOn(CidadeController.class)
                .porId(pedido.getEnderecoEntrega().cidade().id())).withSelfRel());

        pedidoResponse.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .porId(pedidoResponse.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });

        return pedidoResponse;
    }

    @Override
    public CollectionModel<PedidoResponse> toCollectionModel(Iterable<? extends Pedido> pedidos) {
        return super.toCollectionModel(pedidos)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}
