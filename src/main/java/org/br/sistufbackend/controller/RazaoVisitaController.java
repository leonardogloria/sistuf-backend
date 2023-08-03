package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.RazaoDeVisita;
import org.br.sistufbackend.service.RazaoDeVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/razaoVisita")
@CrossOrigin(origins = "*")
public class RazaoVisitaController {
    @Autowired
    RazaoDeVisitaService razaoDeVisitaService;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<RazaoDeVisita> all = razaoDeVisitaService.getAll();
        return ResponseEntity.ok(all);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody RazaoDeVisita razaoDeVisita){
        RazaoDeVisita saved = razaoDeVisitaService.create(razaoDeVisita);
        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){

        Long count = razaoDeVisitaService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("*"));
        List<RazaoDeVisita> all;

        if(nome != null && !nome.isEmpty()){
            all = razaoDeVisitaService.findAllByRotulo(nome);
        }else{
            all =  razaoDeVisitaService.getAll(size,page);
        }

        return ResponseEntity.ok().headers(httpHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable  Long id){
        try {
            RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(razaoDeVisita);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        razaoDeVisitaService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody RazaoDeVisita razaoDeVisita){
        razaoDeVisitaService.update(id,razaoDeVisita);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));


    }
}
