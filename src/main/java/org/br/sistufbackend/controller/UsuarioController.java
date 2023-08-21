package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.exception.UsuarioValidationException;
import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    HeaderService headerService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = usuarioService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<Usuario> all ;
        if(nome != null && !nome.isEmpty()){
            all = usuarioService.getAll();
        }else{
            all = usuarioService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable String id){
        try {
            Usuario usuario = usuarioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        usuarioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Usuario usuario){
        try{
            Usuario saved = usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        }catch (UsuarioValidationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody Usuario usuario){
        usuarioService.update(id,usuario);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }



}
