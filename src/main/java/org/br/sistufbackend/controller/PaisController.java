package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Pais;
import org.br.sistufbackend.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pais")
@CrossOrigin(origins = "*")
public class PaisController {
    @Autowired
    PaisService paisService;
    @PostMapping
    public ResponseEntity create(@RequestBody Pais pais){
        Pais saved = paisService.save(pais);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Pais pais = paisService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(pais);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }

    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<Pais> all = paisService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page
                                 ){
        List<Pais> paises;
        Long count = paisService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(List.of("*"));
        if(nome != null && !nome.isEmpty()){
            paises = paisService.findAllByNome(nome);
        }else {
            paises = paisService.getAll(size,page);
        }
        return ResponseEntity.ok().headers(httpHeaders).body(paises);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable  Long id){
        try{
            paisService.deleteById(id);
            return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Esse País esta sendo utilizado em algum recurso e não pode ser deletado."));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody Pais pais){
        paisService.update(id,pais);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
