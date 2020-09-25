package com.food.api.model.request;

import com.food.api.validation.FileSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "500KB")
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