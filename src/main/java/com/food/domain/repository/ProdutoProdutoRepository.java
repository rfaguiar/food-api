package com.food.domain.repository;

import com.food.domain.model.FotoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoProdutoRepository extends JpaRepository<FotoProduto, Long> {
}
