package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.TipoNavio;
import org.br.sistufbackend.service.TipoNavioService;
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
@RequestMapping("/tipoNavio")
@CrossOrigin(origins = "*")
public class TipoNavioController {
    @Autowired
    TipoNavioService navioService;
    @PostMapping
    public ResponseEntity create(@RequestBody TipoNavio navio){
        TipoNavio saved = navioService.save(navio);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<TipoNavio> all = navioService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam(required = false)  String nome,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "0") Integer page){
        List<TipoNavio> all;
        Long count = navioService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(Arrays.asList("*"));

        if(nome != null && !nome.isEmpty()){
            all = navioService.findAllByNome(nome);
        }else{
            all =  navioService.getAll(size, page);
        }



        return ResponseEntity.ok().headers(httpHeaders).body(all);
    }
    @GetMapping("/{id}")
    public  ResponseEntity findById(@PathVariable  Long id){
        try{
            TipoNavio navio = navioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(navio);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        navioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody TipoNavio navio){
        navioService.update(id, navio);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
