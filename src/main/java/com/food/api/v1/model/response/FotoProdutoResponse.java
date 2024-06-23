package com.food.api.v1.model.response;

import com.food.domain.model.FotoProduto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "fotos")
public class FotoProdutoResponse extends RepresentationModel<FotoProdutoResponse> {

    @Schema(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
    private String nomeArquivo;
    @Schema(example = "Prime Rib ao ponto")
    private String descricao;
    @Schema(example = "image/jpeg")
    private String contentType;
    @Schema(example = "202912")
    private Long tamanho;

    public FotoProdutoResponse(FotoProduto fotoProduto) {
        this(fotoProduto.nomeArquivo(), fotoProduto.descricao(), fotoProduto.contentType(), fotoProduto.tamanho());
    }

    public FotoProdutoResponse(String nomeArquivo, String descricao, String contentType, Long tamanho) {
        this.nomeArquivo = nomeArquivo;
        this.descricao = descricao;
        this.contentType = contentType;
        this.tamanho = tamanho;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getTamanho() {
        return tamanho;
    }
}
