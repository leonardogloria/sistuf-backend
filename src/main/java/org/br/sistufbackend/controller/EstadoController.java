package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/estado")
public class EstadoController {
    @Autowired
    EstadoService estadoService;
    @PostMapping
    public ResponseEntity create(@RequestBody  Estado estado){
        Estado saved = estadoService.save(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(estadoService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Estado byId = estadoService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(byId);
        }catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Messagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        estadoService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensage", "Sucesso"));
    }
    @PostMapping("/{id}")
    public ResponseEntity updateById(@PathVariable Long id, @RequestBody Estado estado){
        estadoService.update(id, estado);
        return ResponseEntity.ok(Map.of("Mensage", "Sucesso"));

    }
}
