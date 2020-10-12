package com.food.api.v1.assembler;

import com.food.api.v1.controller.PedidoController;
import com.food.api.v1.model.response.PedidoResumoResponse;
import com.food.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResumoResponseAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoResponse> {

    private final FoodLinks foodLinks;

    @Autowired
    public PedidoResumoResponseAssembler(FoodLinks foodLinks) {
        super(PedidoController.class, PedidoResumoResponse.class);
        this.foodLinks = foodLinks;
    }

    @Override
    public PedidoResumoResponse toModel(Pedido pedido) {
        return new PedidoResumoResponse(pedido)
                .add(foodLinks.linkToPedido(pedido.getCodigo()))
                .add(foodLinks.linkToPedidos("pedidos"))
                .addRestauranteLink(foodLinks.linkToRestaurante(pedido.getRestaurante().id()))
                .addClientLink(foodLinks.linkToUsuario(pedido.getCliente().id()));
    }

    @Override
    public CollectionModel<PedidoResumoResponse> toCollectionModel(Iterable<? extends Pedido> pedidos) {
        return super.toCollectionModel(pedidos)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}
