package com.food.api.assembler;

import com.food.api.controller.PedidoController;
import com.food.api.controller.RestauranteController;
import com.food.api.controller.UsuarioController;
import com.food.api.model.response.PedidoResumoResponse;
import com.food.domain.model.Pedido;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoResponseAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoResponse> {

    public PedidoResumoResponseAssembler() {
        super(PedidoController.class, PedidoResumoResponse.class);
    }

    @Override
    public PedidoResumoResponse toModel(Pedido pedido) {
        PedidoResumoResponse pedidoResponse = new PedidoResumoResponse(pedido);

        pedidoResponse.add(linkTo(
                methodOn(PedidoController.class).porId(pedido.getCodigo())
        ).withSelfRel());

        pedidoResponse.add(linkTo(PedidoController.class).withRel("pedidos"));

        pedidoResponse.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .porId(pedido.getRestaurante().id())).withSelfRel());

        pedidoResponse.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .porId(pedido.getCliente().id())).withSelfRel());

        return pedidoResponse;
    }

    @Override
    public CollectionModel<PedidoResumoResponse> toCollectionModel(Iterable<? extends Pedido> pedidos) {
        return super.toCollectionModel(pedidos)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}
