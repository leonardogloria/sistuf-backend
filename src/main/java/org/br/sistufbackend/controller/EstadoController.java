package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.service.EstadoService;
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
@RequestMapping("/estado")
@CrossOrigin(origins = "*")
public class EstadoController {
    @Autowired
    EstadoService estadoService;
    @PostMapping
    public ResponseEntity create(@RequestBody  Estado estado){
        Estado saved = estadoService.save(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<Estado> all = estadoService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = estadoService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("*"));

        if(nome != null && !nome.isEmpty()){
            return ResponseEntity.ok(estadoService.findAllByName(nome));
        }
        return ResponseEntity.ok().headers(httpHeaders).body(estadoService.getAll(size,page));
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        try{
            Estado byId = estadoService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(byId);
        }catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Messagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        try{
            estadoService.deleteById(id);
            return ResponseEntity.ok(Map.of("Mensage", "Sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Esse Estado esta sendo utilizado em algum recurso e não pode ser deletado."));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable String id, @RequestBody Estado estado){
        estadoService.update(id, estado);
        return ResponseEntity.ok(Map.of("Mensage", "Sucesso"));
    }
}
