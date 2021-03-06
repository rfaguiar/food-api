package com.food.domain.repository;

import com.food.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

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
            where p.codigo = :codigo
            """)
    Optional<Pedido> findByCodigo(String codigo);

    @Query("""
            select 
                case when count(1) > 0 then true 
                else false 
                end
            from Pedido ped
                join ped.restaurante rest
                join rest.responsaveis resp
            where ped.codigo = :codigoPedido\s
                and resp.id = :usuarioId
            
            """)
    boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);
}
