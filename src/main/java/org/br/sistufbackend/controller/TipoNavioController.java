package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.TipoNavio;
import org.br.sistufbackend.service.TipoNavioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tipoNavio")
@CrossOrigin(origins = "*")
public class TipoNavioController {
    @Autowired
    TipoNavioService navioService;
    @PostMapping
    public ResponseEntity create(@RequestBody TipoNavio navio){
        TipoNavio saved = navioService.save(navio);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping
    public ResponseEntity findAll(){
        List<TipoNavio> all = navioService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/{id}")
    public  ResponseEntity findById(@PathVariable  Long id){
        try{
            TipoNavio navio = navioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(navio);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        navioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody TipoNavio navio){
        navioService.update(id, navio);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
