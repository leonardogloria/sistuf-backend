package org.br.sistufbackend.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.model.dto.PortoDTO;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.PortoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/porto")
@CrossOrigin(origins = "*")
public class PortoController {
    @Autowired
    PortoService portoService;
    @Autowired
    HeaderService headerService;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<PortoDTO> all = portoService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){

        Long count = portoService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<Porto> all ;
        if(nome != null && !nome.isEmpty()){
            all = portoService.findByName(nome);
        }else{
            all = portoService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable  Long id){
        try{
            Porto porto = portoService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(porto);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Porto porto){
        Porto saved = portoService.save(porto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByID(@PathVariable  Long id){
        portoService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));

    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Porto porto){
        portoService.update(id,porto);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));

    }
}
