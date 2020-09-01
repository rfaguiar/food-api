package com.food.infrastructure.service;

import com.food.domain.repository.RestauranteRepository;
import com.food.service.RestauranteService;
import com.food.service.model.RestauranteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    @Autowired
    public RestauranteServiceImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public List<RestauranteDto> todos() {
        return restauranteRepository.todos()
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RestauranteDto> buscarPorId(Long restauranteId) {
        return restauranteRepository.porId(restauranteId)
                .map(RestauranteDto::new);
    }
}
