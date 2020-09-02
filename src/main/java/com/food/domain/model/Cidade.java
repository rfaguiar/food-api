package com.food.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public record Cidade (@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
                      String nome,
                      @ManyToOne
                      @JoinColumn(name = "estado_id")
                      Estado estado) {
    public Cidade {}
}
