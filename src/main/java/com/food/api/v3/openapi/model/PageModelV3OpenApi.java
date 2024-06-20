package com.food.api.v3.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(defaultValue = "PageModel")
public class PageModelV3OpenApi {

    @Schema(example = "10", description = "Quantidade de registros por página")
    private Long size;

    @Schema(example = "50", description = "Total de registros")
    private Long totalElements;

    @Schema(example = "5", description = "Total de páginas")
    private Long totalPages;

    @Schema(example = "0", description = "Número da página (começa em 0)")
    private Long number;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
