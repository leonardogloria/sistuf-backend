package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.service.OrganizacaoMilitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organizacaoMilitar")
public class OrganizacaoMilitarController {
    @Autowired
    OrganizacaoMilitarService organizacaoMilitarService;
    @GetMapping
    public ResponseEntity getAll(){
        List<OrganizacaoMilitar> all = organizacaoMilitarService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try {
            OrganizacaoMilitar organizacaoMilitar = organizacaoMilitarService.getById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(organizacaoMilitar);

        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @PostMapping
    public ResponseEntity create(@RequestBody OrganizacaoMilitar om){
        OrganizacaoMilitar saved = organizacaoMilitarService.save(om);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        organizacaoMilitarService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody OrganizacaoMilitar om){
        organizacaoMilitarService.update(id, om);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado com sucesso"));

    }

}
