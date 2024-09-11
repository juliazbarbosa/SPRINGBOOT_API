package br.edu.univille.poo.JPA.Controller;

import br.edu.univille.poo.JPA.Entity.Tarefa;
import br.edu.univille.poo.JPA.Service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping("api/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> obterTodos(){
        return tarefaService.obtertodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa>obterPeloId(@PathVariable Long id){
        var opt = tarefaService.obterPeloId(id);
        return opt.map(tarefa -> new ResponseEntity<>(tarefa, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> incluir(@RequestBody Tarefa tarefa){
        try {
            tarefa = tarefaService.incluir(tarefa);
            return  new ResponseEntity<>(tarefa, HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody Tarefa tarefa){
        try {
            tarefa = tarefaService.atualizar(tarefa);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //Exclusao formato json que precise colocar isso localhost:8080/api/tarefa/id e no json so id
    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestBody Tarefa tarefa){
        try {
            tarefaService.excluir(tarefa);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
    
    // Exclusao formato localhost:8080/api/tarefa/id so colocar isso no postman que deleta
     /*@DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            tarefaService.excluir(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Utilizando NO_CONTENT para exclusão bem-sucedida
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping("/nao-finalizadas")
    public List<Tarefa> obterNaoFinalizadas() {
        return tarefaService.obterNaoFinalizadas();
    }

    @GetMapping("/concluídas")
    public List<Tarefa> obterFinalizadas() {
        return tarefaService.obterFinalizadas();
    }

    @GetMapping("/em atraso")
    public List<Tarefa> obterAtrasadas() {
        return tarefaService.obterAtrasadas();
    }

    @GetMapping("/nao-finalizadas-entre/{inicio}/{fim}")
    public List<Tarefa> obterNaoFinalizadasEntreDatas(@PathVariable String inicio, @PathVariable String fim) {
        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);
        return tarefaService.obterNaoFinalizadasEntreDatas(dataInicio, dataFim);
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizar(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.finalizarTarefa(id);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
