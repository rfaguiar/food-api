package com.food.api.controller;

import com.food.api.model.request.FormaPagamentoRequest;
import com.food.api.model.response.FormaPagamentoResponse;
import com.food.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.food.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    private final FormaPagamentoService formaPagamentoService;

    @Autowired
    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @Override
    @GetMapping
    public List<FormaPagamentoResponse> listar() {
        return formaPagamentoService.buscarFormaPagamento();
    }

    @Override
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoResponse> buscar(@PathVariable Long formaPagamentoId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoService.buscarPorId(formaPagamentoId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoResponse cadastrar(@RequestBody @Valid FormaPagamentoRequest formaPagamentoDto) {
        return formaPagamentoService.cadastrar(formaPagamentoDto);
    }

    @Override
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoResponse atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoRequest formaPagamentoDto) {
        return formaPagamentoService.atualizar(formaPagamentoId, formaPagamentoDto);
    }

    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.remover(formaPagamentoId);
    }
}
