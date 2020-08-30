package com.food.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.food.service.model.CozinhaDTO;

import java.util.List;

@JacksonXmlRootElement(localName = "cozinhas")
public record CozinhasXmlWrapper(@JacksonXmlElementWrapper(useWrapping = false)
                                 @JsonProperty("cozinha")
                                 List<CozinhaDTO> cozinhas) {
}
