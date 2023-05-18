package org.br.sistufbackend.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.service.PortoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/porto")
public class PortoController {
    @Autowired
    PortoService portoService;
    @GetMapping
    public ResponseEntity getAll(){
        List<Porto> all = portoService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable  Long id){
        try{
            Porto porto = portoService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(porto);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Porto porto){
        Porto saved = portoService.save(porto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByID(@PathVariable  Long id){
        portoService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));

    }
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Porto porto){
        portoService.update(id,porto);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));

    }
}
