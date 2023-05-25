package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.CategoriaVisita;
import org.br.sistufbackend.service.CategoriaVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categoriaVisita")
public class CategoriaVisitaController {
    @Autowired
    CategoriaVisitaService categoriaVisitaService;
    @PostMapping
    public ResponseEntity create(@RequestBody  CategoriaVisita categoriaVisita){
        CategoriaVisita saved = categoriaVisitaService.save(categoriaVisita);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try {
            CategoriaVisita categoriaVisita = categoriaVisitaService.getById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(categoriaVisita);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size){
        List<CategoriaVisita> all = new ArrayList<>();
        if(nome != null && !nome.isEmpty()){
            all = categoriaVisitaService.findByDescricaoDetalhada(nome,size);
        }else{
            all = categoriaVisitaService.getAll(size);
        }
        return ResponseEntity.ok(all);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        categoriaVisitaService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));

    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody CategoriaVisita categoriaVisita){
        categoriaVisitaService.update(id,categoriaVisita);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }
}
