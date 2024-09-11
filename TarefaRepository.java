package br.edu.univille.poo.JPA.Repository;

import br.edu.univille.poo.JPA.Entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findAllByFinalizadoFalse();
    List<Tarefa> findAllByFinalizadoTrue();
    List<Tarefa> findAllByDataPrevistaFinalizacaoBeforeAndFinalizadoFalse(LocalDate data);
    List<Tarefa> findAllByDataPrevistaFinalizacaoBetweenAndFinalizadoFalse(LocalDate inicio, LocalDate fim);
}
