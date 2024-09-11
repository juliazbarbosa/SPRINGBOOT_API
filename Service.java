package br.edu.univille.poo.JPA.Service;

import br.edu.univille.poo.JPA.Entity.Tarefa;
import br.edu.univille.poo.JPA.Repository.TarefaRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa>obtertodos(){
        return tarefaRepository.findAll();
    }
    public Optional<Tarefa> obterPeloId(Long id){
        return tarefaRepository.findById(id);
    }
    public Tarefa incluir(Tarefa tarefa){
        if(Strings.isBlank(tarefa.getTitulo()) || tarefa.getTitulo().length() < 5){
            throw new RuntimeException("Título inválido. Deve ter pelo menos 5 caracteres.");
        }
        if (tarefa.getDataPrevistaFinalizacao() == null){
            throw new RuntimeException("Data prevista de finalização não informada.");
        }
        tarefa.setId(0);
        return tarefaRepository.save(tarefa);
    }
    public Tarefa atualizar(Tarefa tarefa){
        Tarefa antiga = tarefaRepository.findById(tarefa.getId()).orElse(null);
        if (antiga == null){
            throw new RuntimeException("Tarefa não encontrada");
        }
        if (antiga.isFinalizado()){
            throw new RuntimeException("Tarefa finalizada não pode ser modificada");
        }
        if (Strings.isBlank(tarefa.getTitulo()) || tarefa.getTitulo().length() < 5){
            throw  new RuntimeException("Tarefa inválida. Deve ter pelo menos 5 caracteres");
        }
        antiga.setTitulo(tarefa.getTitulo());
        antiga.setDescricaoLonga(tarefa.getDescricaoLonga());
        antiga.setDataPrevistaFinalizacao(tarefa.getDataPrevistaFinalizacao());
        return tarefaRepository.save(antiga);
    }

    //Exclusao formato json que precise colocar isso localhost:8080/api/tarefa/id e no json so id
    public void excluir(Tarefa tarefa){
        var antiga = tarefaRepository.findById(tarefa.getId()).orElse(null);
        if (antiga == null){
            throw new RuntimeException("Tarefa não encontrada");
        }
        if (antiga.isFinalizado()){
            throw new RuntimeException("Tarefa finalizada, não pode ser modificada");
        }
        tarefaRepository.delete(antiga);
    }

    // Exclusao formato localhost:8080/api/tarefa/id so colocar isso no postman que deleta
      /*public void excluir(Long id) {
        Tarefa antiga = tarefaRepository.findById(id).orElse(null);
        if (antiga == null) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        if (antiga.isFinalizado()) {
            throw new RuntimeException("Tarefa finalizada, não pode ser excluída");
        }
        tarefaRepository.delete(antiga);
    }*/

    public List<Tarefa> obterNaoFinalizadas(){
        return tarefaRepository.findAllByFinalizadoFalse();
    }
    public List<Tarefa> obterFinalizadas(){
        return tarefaRepository.findAllByFinalizadoTrue();
    }

    public List<Tarefa> obterAtrasadas(){
        return  tarefaRepository.findAllByDataPrevistaFinalizacaoBeforeAndFinalizadoFalse(LocalDate.now());
    }
    public  List<Tarefa> obterNaoFinalizadasEntreDatas(LocalDate inicio, LocalDate fim){
        return tarefaRepository.findAllByDataPrevistaFinalizacaoBetweenAndFinalizadoFalse(inicio,fim);
    }
    public Tarefa finalizarTarefa(Long id){
        Tarefa tarefa = tarefaRepository.findById(id).orElse(null);
        if (tarefa == null){
            throw new RuntimeException("Tarefa não encontrada");
        }
        tarefa.setFinalizado(true);
        tarefa.setDataFinalizacao(LocalDate.now());
        return tarefaRepository.save(tarefa);
    }

}
