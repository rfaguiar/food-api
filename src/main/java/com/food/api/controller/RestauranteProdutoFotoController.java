package com.food.api.controller;

import com.food.api.assembler.FotoProdutoResponseAssembler;
import com.food.api.model.response.FotoProdutoResponse;
import com.food.api.model.response.FotoStreamResponse;
import com.food.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.food.api.validation.FileContentType;
import com.food.api.validation.FileSize;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.service.FotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    private final FotoProdutoService fotoProdutoService;
    private final FotoProdutoResponseAssembler fotoProdutoResponseAssembler;

    @Autowired
    public RestauranteProdutoFotoController(FotoProdutoService fotoProdutoService, FotoProdutoResponseAssembler fotoProdutoResponseAssembler) {
        this.fotoProdutoService = fotoProdutoService;
        this.fotoProdutoResponseAssembler = fotoProdutoResponseAssembler;
    }

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoResponse atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestParam String descricao,
            @FileSize(max = "500KB", message = "A foto de ter um tamanho máximo de 500KB")
            @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE },
                    message = "A foto deve ser do tipo JPG ou PNG")
            @RequestPart MultipartFile arquivo) {
        return fotoProdutoResponseAssembler.toModel(fotoProdutoService.salvar(restauranteId, produtoId, descricao, arquivo));
    }

    @Override
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
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId,
                        @PathVariable Long produtoId) {
        fotoProdutoService.excluir(restauranteId, produtoId);
    }
}
