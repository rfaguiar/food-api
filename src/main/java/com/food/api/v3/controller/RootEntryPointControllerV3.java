package com.food.api.v3.controller;

import com.food.api.v1.model.response.RootEntryPointResponse;
import com.food.api.v3.assembler.FoodLinksV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/v3")
public class RootEntryPointControllerV3 {

    private final FoodLinksV3 foodLinks;

    @Autowired
    public RootEntryPointControllerV3(FoodLinksV3 foodLinks) {
        this.foodLinks = foodLinks;
    }

    @GetMapping
    public RootEntryPointResponse root() {
        return new RootEntryPointResponse()
                .add(foodLinks.linkToCozinhas("cozinhas"))
                .add(foodLinks.linkToCidades("cidades"));
    }
}
