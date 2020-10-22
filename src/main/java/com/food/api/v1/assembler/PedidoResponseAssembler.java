package com.food.api.v1.assembler;

import com.food.api.v1.controller.PedidoController;
import com.food.api.v1.model.response.PedidoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResponseAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public PedidoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(PedidoController.class, PedidoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public PedidoResponse toModel(Pedido pedido) {
        return new PedidoResponse(pedido)
                .add(foodLinks.linkToPedido(pedido.getCodigo()))
                .add(foodLinks.linkToPedidos("pedidos"))
                .addRestauranteLink(foodLinks.linkToRestaurante(pedido.getRestaurante().id()))
                .addClientLink(foodLinks.linkToUsuario(pedido.getCliente().id()))
                .addFormaPagamentoLink(foodLinks.linkToFormaPagamento(pedido.getFormaPagamento().id()))
                .addCidadeEnderecoLink(foodLinks.linkToCidade(pedido.getEnderecoEntrega().cidade().id()))
                .addItensLink(foodLinks)
                .addStatusLink(foodLinks, foodSecurity);
    }

    @Override
    public CollectionModel<PedidoResponse> toCollectionModel(Iterable<? extends Pedido> pedidos) {
        return super.toCollectionModel(pedidos)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}
