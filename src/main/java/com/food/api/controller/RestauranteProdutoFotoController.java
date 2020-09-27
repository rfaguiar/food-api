package com.food.api.controller;

import com.food.api.model.request.FotoProdutoRequest;
import com.food.api.model.response.FotoProdutoResponse;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.service.FotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private final FotoProdutoService fotoProdutoService;

    @Autowired
    public RestauranteProdutoFotoController(FotoProdutoService fotoProdutoService) {
        this.fotoProdutoService = fotoProdutoService;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoResponse atualizarFoto(@PathVariable Long restauranteId,
                                             @PathVariable Long produtoId,
                                             @Valid FotoProdutoRequest fotoProdutoRequest) {
        return fotoProdutoService.salvar(restauranteId, produtoId, fotoProdutoRequest);
    }

    @GetMapping
    public FotoProdutoResponse buscar(@PathVariable Long restauranteId,
                                   @PathVariable Long produtoId) {
        return fotoProdutoService.buscar(restauranteId, produtoId);
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> buscarArquivoFoto(@PathVariable Long restauranteId,
                                                                @PathVariable Long produtoId) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(fotoProdutoService.buscarArquivoFoto(restauranteId, produtoId)));
        } catch (EntidadeNaoEncontradaException ignored) {
            return ResponseEntity.notFound().build();
        }
    }
}
