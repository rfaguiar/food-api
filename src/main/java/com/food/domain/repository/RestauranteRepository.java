package com.food.domain.repository;

import com.food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>,
        JpaSpecificationExecutor<Restaurante>,
        RestauranteRepositoryQueries {

}
