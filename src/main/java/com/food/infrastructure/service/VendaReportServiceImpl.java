package com.food.infrastructure.service;

import com.food.domain.filter.VendaDiariaFilter;
import com.food.service.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class VendaReportServiceImpl implements VendaReportService {

    @Override
    public byte[] consultarVendasDiarias(VendaDiariaFilter filter) {
        return new byte[0];
    }
}
