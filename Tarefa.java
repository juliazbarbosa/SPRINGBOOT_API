package br.edu.univille.poo.JPA.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricaoLonga;

    @Column(nullable = false)
    private boolean finalizado = false;

    @Column(nullable = false)
    private LocalDate dataPrevistaFinalizacao;

    private LocalDate dataFinalizacao;
}
