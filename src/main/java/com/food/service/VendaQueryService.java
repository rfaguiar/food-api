package com.food.service;

import com.food.api.v1.model.dto.VendaDiaria;
import com.food.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
}
