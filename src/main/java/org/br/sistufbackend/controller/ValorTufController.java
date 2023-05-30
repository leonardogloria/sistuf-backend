package org.br.sistufbackend.controller;

import org.br.sistufbackend.model.ValorTUF;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.ValorTufService;
import org.springframework.beans.factory.annotation.Autowired;
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
        ValorTUF saved = valorTufService.create(valorTUF);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



}
