package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roteiro")
@CrossOrigin(origins = "*")
public class RoteiroController {
    @Autowired
    RoteiroService roteiroService;
    @Autowired
    HeaderService headerService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = roteiroService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<Roteiro> all ;
        if(nome != null && !nome.isEmpty()){
            all = roteiroService.getAll();
        }else{
            all = roteiroService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            Roteiro roteiro = roteiroService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(roteiro);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        roteiroService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Roteiro roteiro){
        Roteiro saved = roteiroService.save(roteiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Roteiro roteiro){
        roteiroService.update(id,roteiro);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));

    }




}
