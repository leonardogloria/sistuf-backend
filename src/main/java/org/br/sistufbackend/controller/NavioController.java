package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.exception.NavioValidationException;
import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.dto.NavioDTO;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.NavioService;
import org.br.sistufbackend.service.impl.HeaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/navio")
@CrossOrigin(origins = "*")
public class NavioController {
    @Autowired
    NavioService navioService;
    @Autowired
    private HeaderService headerService;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<NavioDTO> all = navioService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = navioService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<Navio> all ;
        if(nome != null && !nome.isEmpty()){
            all = navioService.findByName(nome);
        }else{
            all = navioService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            Navio navio = navioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(navio);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        navioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Navio navio){
        try{
            Navio saved = navioService.save(navio);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (NavioValidationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    ex.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Navio navio){
        try{
            navioService.update(id,navio);
            return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
        }catch (NavioValidationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    ex.getMessage()));
        }
    }
}
