package com.food.infrastructure.repository.spec;

import com.food.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class RestauranteComFreteGratisSped implements Specification<Restaurante> {

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }
}
