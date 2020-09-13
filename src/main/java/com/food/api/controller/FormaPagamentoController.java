package com.food.api.controller;

import com.food.api.model.request.FormaPagamentoRequest;
import com.food.service.FormaPagamentoService;
import com.food.api.model.response.FormaPagamentoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public List<FormaPagamentoResponse> listar() {
        return formaPagamentoService.buscarFormaPagamento();
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoResponse buscar(@PathVariable Long formaPagamentoId) {
        return formaPagamentoService.buscarPorId(formaPagamentoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoResponse cadastrar(@RequestBody @Valid FormaPagamentoRequest formaPagamentoDto) {
        return formaPagamentoService.cadastrar(formaPagamentoDto);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoResponse atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoRequest formaPagamentoDto) {
        return formaPagamentoService.atualizar(formaPagamentoId, formaPagamentoDto);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.remover(formaPagamentoId);
    }
}
