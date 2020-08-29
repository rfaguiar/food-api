package com.food.jpa;

import com.food.Application;
import com.food.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class CadastroCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
        Cozinha cozinhaBr = new Cozinha();
        cozinhaBr.setNome("Brasileira");
        System.out.println(cadastroCozinha.adicionar(cozinhaBr));

        Cozinha cozinhaJap = new Cozinha();
        cozinhaJap.setNome("Japoneza");
        System.out.println(cadastroCozinha.adicionar(cozinhaJap));
    }
}
