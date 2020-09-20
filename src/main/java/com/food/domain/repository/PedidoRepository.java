package com.food.domain.repository;

import com.food.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query( """
            from Pedido p
            left join fetch p.itens it
            inner join fetch it.produto
            inner join fetch p.cliente
            inner join fetch p.restaurante r
            inner join fetch r.cozinha
            inner join fetch p.formaPagamento
            inner join fetch p.enderecoEntrega.cidade c
            inner join fetch c.estado
            where p.id = :pedidoId
            """)
    Optional<Pedido> findById(Long pedidoId);

    @Query( """
            from Pedido p
            inner join fetch p.cliente
            inner join fetch p.restaurante r
            inner join fetch r.cozinha
            inner join fetch p.formaPagamento
            inner join fetch p.enderecoEntrega.cidade c
            inner join fetch c.estado
            """)
    List<Pedido> findAll();

}
