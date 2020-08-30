package com.food.jpa;

import com.food.Application;
import com.food.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaEstadoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);
        estados.todos()
            .forEach(System.out::println);
    }
}
