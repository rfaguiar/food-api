package com.food.api.v1.assembler;

import com.food.api.v1.controller.PedidoController;
import com.food.api.v1.model.response.PedidoResumoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResumoResponseAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public PedidoResumoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(PedidoController.class, PedidoResumoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public PedidoResumoResponse toModel(Pedido pedido) {
        PedidoResumoResponse pedidoResumoResponse = new PedidoResumoResponse(pedido)
                .add(foodLinks.linkToPedido(pedido.getCodigo()));

        if (foodSecurity.podePesquisarPedidos()) {
            pedidoResumoResponse.add(foodLinks.linkToPedidos("pedidos"));
        }

        if (foodSecurity.podeConsultarRestaurantes()) {
            pedidoResumoResponse.addRestauranteLink(foodLinks.linkToRestaurante(pedido.getRestaurante().id()));
        }

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoResumoResponse.addClientLink(foodLinks.linkToUsuario(pedido.getCliente().id()));
        }

        return pedidoResumoResponse;
    }

    @Override
    public CollectionModel<PedidoResumoResponse> toCollectionModel(Iterable<? extends Pedido> pedidos) {
        return super.toCollectionModel(pedidos)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}
