package com.food.api.openapi.model;

import io.swagger.annotations.ApiModel;

@ApiModel("Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @ApiModel("Link")
    private class LinkModel {
        private String href;
        private String templated;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTemplated() {
            return templated;
        }

        public void setTemplated(String templated) {
            this.templated = templated;
        }
    }

    public LinkModel getRel() {
        return rel;
    }

    public void setRel(LinkModel rel) {
        this.rel = rel;
    }
}