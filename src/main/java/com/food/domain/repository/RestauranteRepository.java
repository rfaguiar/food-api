package com.food.domain.repository;

import com.food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query("""
            from Restaurante r 
            inner join fetch r.cozinha 
            left join fetch r.formasPagamento
            """)
    List<Restaurante> findAll();

    @Query("""
            from Restaurante r 
                inner join fetch r.cozinha 
                left join fetch r.formasPagamento 
                left join fetch r.endereco.cidade c
                left join fetch c.estado
                left join fetch r.responsaveis
            where r.id = :idRestaurante
            """)
    Optional<Restaurante> findById(Long idRestaurante);

}
