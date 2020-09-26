package com.food.api.model.request;

import com.food.api.validation.FileContentType;
import com.food.api.validation.FileSize;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "500KB", message = "A foto de ter um tamanho máximo de 500KB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE },
            message = "A foto deve ser do tipo JPG ou PNG")
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;

    public void setArquivo(MultipartFile arquivo) {
        this.arquivo = arquivo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MultipartFile getArquivo() {
        return arquivo;
    }

    public String getDescricao() {
        return descricao;
    }
}