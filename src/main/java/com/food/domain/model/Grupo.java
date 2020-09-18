package com.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
                    @ManyToMany(fetch = FetchType.EAGER)
                    @JoinTable(name = "grupo_permissao",
                            joinColumns = @JoinColumn(name = "grupo_id"),
                            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
                    Set<Permissao> permissoes) {
    public Grupo() {
        this(null, null, new HashSet<>());
    }

    public boolean removerPermissao(Permissao permissao) {
        return permissoes.remove(permissao);
    }

    public boolean adicionarPermissao(Permissao permissao) {
        return permissoes.add(permissao);
    }
}
