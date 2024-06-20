package com.food.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(defaultValue = "Pageable")
public class PageableModelOpenApi {

    @Schema(example = "0", description = "Número de página (começa em 0)")
    private int page;
    @Schema(example = "10", description = "Quantidade de elementos por página")
    private int size;
    @Schema(example = "nome,asc", description = "Nome da propriedade para ordenação")
    private List<String> sort;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
