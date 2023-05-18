package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.service.NavioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/navio")
public class NavioController {
    @Autowired
    NavioService navioService;
    @GetMapping
    public ResponseEntity getAll(){
        List<Navio> all = navioService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            Navio navio = navioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(navio);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        navioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Navio navio){
        Navio saved = navioService.save(navio);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Navio navio){
        navioService.update(id,navio);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));

    }
}
