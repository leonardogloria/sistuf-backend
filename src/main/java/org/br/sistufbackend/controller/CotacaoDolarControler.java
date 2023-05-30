package org.br.sistufbackend.controller;

import org.br.sistufbackend.model.CotacaoDolar;
import org.br.sistufbackend.model.RazaoDeVisita;
import org.br.sistufbackend.service.CotacaoDolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cotacaoDolar")
@CrossOrigin(origins = "*")
public class CotacaoDolarControler {
    @Autowired
    CotacaoDolarService cotacaoDolarService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = cotacaoDolarService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(List.of("*"));
        List<CotacaoDolar> all;
        if(nome != null && !nome.isEmpty()){
            all = null;
        }else{
            all =  cotacaoDolarService.getAll(size,page);
        }
        return ResponseEntity.ok().headers(httpHeaders).body(all);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CotacaoDolar cotacaoDolar){
        CotacaoDolar saved = cotacaoDolarService.save(cotacaoDolar);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable LocalDate id){
        cotacaoDolarService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
}
