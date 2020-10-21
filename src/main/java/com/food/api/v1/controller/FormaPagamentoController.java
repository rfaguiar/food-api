package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.FormaPagamentoResponseAssembler;
import com.food.api.v1.model.request.FormaPagamentoRequest;
import com.food.api.v1.model.response.FormaPagamentoResponse;
import com.food.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.food.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoResponseAssembler formaPagamentoResponseAssembler;

    @Autowired
    public FormaPagamentoController(FormaPagamentoService formaPagamentoService, FormaPagamentoResponseAssembler formaPagamentoResponseAssembler) {
        this.formaPagamentoService = formaPagamentoService;
        this.formaPagamentoResponseAssembler = formaPagamentoResponseAssembler;
    }

    @Override
    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoResponse> listar() {
        return formaPagamentoResponseAssembler.toCollectionModel(formaPagamentoService.buscarFormaPagamento());
    }

    @Override
    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoResponse> porId(@PathVariable Long formaPagamentoId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoResponseAssembler.toModel(formaPagamentoService.buscarPorId(formaPagamentoId)));
    }

    @Override
    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoResponse cadastrar(@RequestBody @Valid FormaPagamentoRequest formaPagamentoDto) {
        return formaPagamentoResponseAssembler.toModel(formaPagamentoService.cadastrar(formaPagamentoDto));
    }

    @Override
    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoResponse atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoRequest formaPagamentoDto) {
        return formaPagamentoResponseAssembler.toModel(formaPagamentoService.atualizar(formaPagamentoId, formaPagamentoDto));
    }

    @Override
    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.remover(formaPagamentoId);
    }
}
