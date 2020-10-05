package com.food.api.openapi.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

abstract class PagedModelOpenApi<T> {

    private List<T> content;
    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private Long size;
    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;
    @ApiModelProperty(example = "5", value = "Total de páginas")
    private Long totalPages;
    @ApiModelProperty(example = "0", value = "Número de página (começa em 0)")
    private Long number;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

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
