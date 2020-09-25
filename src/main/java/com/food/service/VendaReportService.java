package com.food.service;

import com.food.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
    byte[] consultarVendasDiarias(VendaDiariaFilter filter);
}
