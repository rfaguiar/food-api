package com.food.api;

import com.food.api.v1.model.response.RootEntryPointResponse;
import com.food.api.v2.controller.RootEntryPointControllerV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ApiIgnore
@RestController
@RequestMapping("/")
public class RootEntryPointController {


    @GetMapping
    public RootEntryPointResponse root() {
        return new RootEntryPointResponse()
                .add(linkTo(com.food.api.v1.controller.RootEntryPointControllerV1.class).withRel("v1"))
                .add(linkTo(RootEntryPointControllerV2.class).withRel("v2"));
    }
}
