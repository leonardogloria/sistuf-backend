package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.CategoriaVisita;
import org.br.sistufbackend.service.CategoriaVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categoriaVisita")

public class CategoriaVisitaController {
    @Autowired
    CategoriaVisitaService categoriaVisitaService;

    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<CategoriaVisita> all = categoriaVisitaService.getAll();
        return ResponseEntity.ok(all);
    }
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
            return ok(categoriaVisita);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        List<CategoriaVisita> all = new ArrayList<>();
        Long count = categoriaVisitaService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("*"));

        if(nome != null && !nome.isEmpty()){
            all = categoriaVisitaService.findByDescricaoDetalhada(nome,size);
        }else{
            all = categoriaVisitaService.getAll(size,page);
        }

        return ResponseEntity.ok().headers(httpHeaders).body(all);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try{
            categoriaVisitaService.deleteById(id);
            return ok(Map.of("Mensagem", "Deletado com sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Essa Categoria esta sendo utilizada em algum recurso e não pode ser deletada."));
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody CategoriaVisita categoriaVisita){
        categoriaVisitaService.update(id,categoriaVisita);
        return ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }
}
