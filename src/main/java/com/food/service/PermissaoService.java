package com.food.service;

import com.food.api.model.response.PermissaoResponse;

public interface PermissaoService {

    PermissaoResponse buscarOuFalhar(Long permissaoId);
}
