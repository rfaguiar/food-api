package com.food.infrastructure.service;

import com.food.api.model.dto.VendaDiaria;
import com.food.domain.filter.VendaDiariaFilter;
import com.food.domain.model.Pedido;
import com.food.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        var functionDateDataCriacao= builder.function(
                "date", Date.class, root.get("dataCriacao"));

        var selection = builder.construct(
                VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));
        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }
}
