package org.br.sistufbackend.controller;

import org.br.sistufbackend.exception.TonelagemValidationException;
import org.br.sistufbackend.model.ValorTUF;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.ValorTufService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/valorTuf")
@CrossOrigin(origins = "*")
public class ValorTufController {
    @Autowired
    ValorTufService valorTufService;
    @Autowired
    HeaderService headerService;
    @GetMapping
    public ResponseEntity getAll(){
        Long count = valorTufService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<ValorTUF> all = valorTufService.getAll();
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody ValorTUF valorTUF){
        try{
            ValorTUF saved = valorTufService.create(valorTUF);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (TonelagemValidationException ex){
            System.out.println(ex.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    ex.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        try{
            valorTufService.deleteById(id);
            return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Essa Regra esta sendo utilizada em algum recurso e não pode ser deletada."));
        }
    }

}
