package com.food.api.controller;

import com.food.service.FormaPagamentoService;
import com.food.service.model.FormaPagamentoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService formaPagamentoService;

    @Autowired
    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping
    public List<FormaPagamentoDto> listar() {
        return formaPagamentoService.buscarFormaPagamento();
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDto buscar(@PathVariable Long formaPagamentoId) {
        return formaPagamentoService.buscarPorId(formaPagamentoId);
    }
}
