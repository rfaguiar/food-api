package com.food.infrastructure.service;

import com.food.api.v1.model.dto.VendaDiaria;
import com.food.domain.filter.VendaDiariaFilter;
import com.food.domain.model.Pedido;
import com.food.domain.model.StatusPedido;
import com.food.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);
        var predicates = new ArrayList<Predicate>();

        if (filtro.restauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filtro.restauranteId()));
        }

        if (filtro.dataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.dataCriacaoInicio()));
        }

        if (filtro.dataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                    filtro.dataCriacaoFim()));
        }

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        var functionDateDataCriacao= builder.function(
                "date", Date.class, root.get("dataCriacao"));

        var selection = builder.construct(
                VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));
        query.select(selection);
        query.groupBy(functionDateDataCriacao);
        query.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }
}
