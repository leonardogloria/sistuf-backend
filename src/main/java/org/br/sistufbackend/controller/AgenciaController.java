package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {
    @Autowired
    AgenciaService agenciaService;
    @GetMapping
    public ResponseEntity getAll(){
        List<Agencia> all = agenciaService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Agencia agencia = agenciaService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(agencia);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Messagem", "Não Localizado"));
        }
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Agencia agencia){
        Agencia saved = agenciaService.save(agencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        agenciaService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensage", "Deletado com Sucesso"));
    }
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Agencia agencia){
        agenciaService.update(id,agencia);
        return ResponseEntity.ok(Map.of("Mensage", "Alterado com Sucesso"));
    }
}
