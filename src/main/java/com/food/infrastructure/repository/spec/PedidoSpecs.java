package com.food.infrastructure.repository.spec;

import com.food.domain.model.Pedido;
import com.food.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.fetch("restaurante");
            root.fetch("cliente");
            var predicates = new ArrayList<Predicate>();
            if (filtro.clienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.clienteId()));
            }
            if (filtro.restauranteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.restauranteId()));
            }
            if (filtro.dataCriacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.dataCriacaoInicio()));
            }
            if (filtro.dataCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.dataCriacaoFim()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
