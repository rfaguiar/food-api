package com.food.api.v1.model.request;

import com.food.api.v1.validation.FileContentType;
import com.food.api.v1.validation.FileSize;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record FotoProdutoRequest(
        @NotNull
        @FileSize(max = "500KB")
        @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
        MultipartFile arquivo,
        @NotBlank
        String descricao
) {
}
