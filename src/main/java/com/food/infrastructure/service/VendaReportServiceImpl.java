package com.food.infrastructure.service;

import com.food.domain.exception.ReportException;
import com.food.domain.filter.VendaDiariaFilter;
import com.food.service.VendaQueryService;
import com.food.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class VendaReportServiceImpl implements VendaReportService {

    private final VendaQueryService vendaQueryService;

    @Autowired
    public VendaReportServiceImpl(VendaQueryService vendaQueryService) {
        this.vendaQueryService = vendaQueryService;
    }

    @Override
    public byte[] consultarVendasDiarias(VendaDiariaFilter filter) {
        try {
            var inputStream = getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");
            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
            var datasource = new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filter));
            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, datasource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("Não foi possivel emitir relatório de vendas diárias", e);
        }
    }
}
