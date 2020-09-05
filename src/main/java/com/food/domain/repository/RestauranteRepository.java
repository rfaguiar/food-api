package com.food.domain.repository;

import com.food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>,
        JpaSpecificationExecutor<Restaurante>,
        RestauranteRepositoryQueries {

    @Query("""
            from Restaurante r 
            inner join fetch r.cozinha 
            left join fetch r.formasPagamento
            """)
    List<Restaurante> findAll();
}
