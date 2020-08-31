package com.food.infrastructure.service;

import com.food.domain.repository.EstadoRepository;
import com.food.service.EstadoService;
import com.food.service.model.EstadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @Override
    public List<EstadoDto> todos() {
        return estadoRepository.todos()
                .map(EstadoDto::new)
                .collect(Collectors.toList());
    }
}
