package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public record Grupo(@Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    Long id,
                    @Column(nullable = false)
                    String nome,
                    @ManyToMany
                    @JoinTable(name = "grupo_permissao",
                            joinColumns = @JoinColumn(name = "grupo_id"),
                            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
                    Set<Permissao> permissoes) {
    public Grupo() {
        this(null, null, new HashSet<>());
    }
}
