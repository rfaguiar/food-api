package com.food.domain.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public record Usuario(@Id
                      @GeneratedValue(strategy = GenerationType.IDENTITY)
                      Long id,
                      @Column(nullable = false)
                      String nome,
                      @Column(nullable = false)
                      String email,
                      @Column(nullable = false)
                      String senha,
                      @CreationTimestamp
                      @Column(nullable = false, columnDefinition = "datetime")
                      LocalDateTime dataCadastro,
                      @ManyToMany
                      @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"),
                              inverseJoinColumns = @JoinColumn(name = "grupo_id"))
                      Set<Grupo> grupos) {
    public Usuario() {
        this(null, null, null, null, null, new HashSet<>());
    }
}
