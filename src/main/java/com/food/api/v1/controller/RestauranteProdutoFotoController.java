package com.food.api.v1.controller;

import com.food.api.security.CheckSecurity;
import com.food.api.v1.assembler.FotoProdutoResponseAssembler;
import com.food.api.v1.model.request.FotoProdutoRequest;
import com.food.api.v1.model.response.FotoProdutoResponse;
import com.food.api.v1.model.response.FotoStreamResponse;
import com.food.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.food.api.v1.validation.FileContentType;
import com.food.api.v1.validation.FileSize;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.service.FotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    private final FotoProdutoService fotoProdutoService;
    private final FotoProdutoResponseAssembler fotoProdutoResponseAssembler;

    @Autowired
    public RestauranteProdutoFotoController(FotoProdutoService fotoProdutoService, FotoProdutoResponseAssembler fotoProdutoResponseAssembler) {
        this.fotoProdutoService = fotoProdutoService;
        this.fotoProdutoResponseAssembler = fotoProdutoResponseAssembler;
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoResponse atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoRequest fotoProdutoRequest) {
        return fotoProdutoResponseAssembler.toModel(
                fotoProdutoService.salvar(
                        restauranteId,
                        produtoId,
                        fotoProdutoRequest.descricao(),
                        fotoProdutoRequest.arquivo()
                )
        );
    }

    @Override
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public FotoProdutoResponse buscar(@PathVariable Long restauranteId,
                                      @PathVariable Long produtoId) {
        return fotoProdutoResponseAssembler.toModel(fotoProdutoService.buscar(restauranteId, produtoId));
    }

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Object> buscarArquivoFoto(@PathVariable Long restauranteId,
                                                    @PathVariable Long produtoId) {
        try {
            FotoStreamResponse fotoStreamResponse = fotoProdutoService.buscarArquivoFoto(restauranteId, produtoId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoStreamResponse.contentType());
            var fotoRecuperada = fotoStreamResponse.fotoArquivo();

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.url())
                        .build();
            }
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.inputStream()));

        } catch (EntidadeNaoEncontradaException ignored) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {
        fotoProdutoService.excluir(restauranteId, produtoId);
    }
}
