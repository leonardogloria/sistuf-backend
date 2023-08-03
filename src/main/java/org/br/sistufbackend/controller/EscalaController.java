package org.br.sistufbackend.controller;

import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.service.EscalaService;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.validation.exception.EscalaValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/escala")
@CrossOrigin(origins = "*")
public class EscalaController {
    @Autowired
    HeaderService headerService;
    @Autowired
    EscalaService escalaService;
    @GetMapping("/roteiro/{id}")
    public ResponseEntity getAllByRoteiro(@PathVariable  Long id){
        List<Escala> allByRoteiroId = escalaService.getAllByRoteiroId(id);
        HttpHeaders customHeaders = headerService
                .getCustomHeaders(Map.of("total-size", String.valueOf(allByRoteiroId.size())));

        return ResponseEntity.ok().headers(customHeaders).body(allByRoteiroId);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Escala escala){

        try{
            Escala saved = escalaService.save(escala);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (EscalaValidationException e){
            //System.out.println(e.getMessage());
            //System.out.println("AQUIIIII");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
    }


}
