package com.food.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(defaultValue = "Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @Schema(defaultValue = "Link")
    private class LinkModel {
        private String href;
        private boolean templated;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public boolean getTemplated() {
            return templated;
        }

        public void setTemplated(boolean templated) {
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
