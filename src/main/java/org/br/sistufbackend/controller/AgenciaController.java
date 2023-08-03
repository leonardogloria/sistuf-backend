package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.CategoriaVisita;
import org.br.sistufbackend.service.AgenciaService;
import org.br.sistufbackend.service.HeaderService;
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
@RequestMapping("/agencia")
@CrossOrigin(origins = "*")
public class AgenciaController {
    @Autowired
    AgenciaService agenciaService;
    @Autowired
    HeaderService headerService;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<Agencia> all = agenciaService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = agenciaService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));

        List<Agencia> all;
        if(nome != null && !nome.isEmpty()){
            all = agenciaService.findByNome(nome);
        }else {
           all = agenciaService.getAll(size,page);
        }

        return ResponseEntity.ok().headers(customHeaders).body(all);
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
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Agencia agencia){
        agenciaService.update(id,agencia);
        return ResponseEntity.ok(Map.of("Mensage", "Alterado com Sucesso"));
    }
}
