package com.food.api;

import com.food.api.v1.model.response.RootEntryPointResponse;
import io.swagger.v3.oas.annotations.Operation;

public interface RootEntryPointControllerOpenApi {

    @Operation(hidden = true)
    RootEntryPointResponse root();
}
