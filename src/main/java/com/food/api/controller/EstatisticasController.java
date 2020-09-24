package com.food.api.controller;

import com.food.api.model.dto.VendaDiaria;
import com.food.domain.filter.VendaDiariaFilter;
import com.food.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;

    @Autowired
    public EstatisticasController(VendaQueryService vendaQueryService) {
        this.vendaQueryService = vendaQueryService;
    }

    @GetMapping("vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
        return vendaQueryService.consultarVendasDiarias(filter);
    }
}
