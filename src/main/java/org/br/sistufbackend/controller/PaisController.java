package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Pais;
import org.br.sistufbackend.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pais")
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
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome){
        List<Pais> paises;
        if(nome != null && !nome.isEmpty()){
            paises = paisService.findAllByNome(nome);
        }else {
            paises = paisService.getAll();
        }
        return ResponseEntity.ok(paises);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable  Long id){
        paisService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody Pais pais){
        paisService.update(id,pais);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
