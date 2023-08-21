package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.exception.GrupoUsuarioValidationException;
import org.br.sistufbackend.exception.UsuarioValidationException;
import org.br.sistufbackend.model.GrupoUsuario;
import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.service.GrupoUsuarioService;
import org.br.sistufbackend.service.HeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grupo-usuario")
@CrossOrigin(origins = "*")
public class GrupoUsuarioController {
    @Autowired
    GrupoUsuarioService grupoUsuarioService;
    @Autowired
    HeaderService headerService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = grupoUsuarioService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<GrupoUsuario> all ;
        if(nome != null && !nome.isEmpty()){
            all = grupoUsuarioService.getAll();
        }else{
            all = grupoUsuarioService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            GrupoUsuario usuario = grupoUsuarioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        grupoUsuarioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody GrupoUsuario grupoUsuario){
        try{
            GrupoUsuario saved = grupoUsuarioService.save(grupoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        }catch (GrupoUsuarioValidationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody GrupoUsuario grupoUsuario){
        grupoUsuarioService.update(id,grupoUsuario);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
