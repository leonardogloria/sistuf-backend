package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.OrganizacaoMilitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organizacaoMilitar")
@CrossOrigin(origins = "*")
public class OrganizacaoMilitarController {
    @Autowired
    OrganizacaoMilitarService organizacaoMilitarService;
    @Autowired
    private HeaderService headerService;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<OrganizacaoMilitar> all = organizacaoMilitarService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        List<OrganizacaoMilitar> all;
        Long count = organizacaoMilitarService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        if(nome != null && !nome.isEmpty()){
            all = organizacaoMilitarService.findByNome(nome);
        }else{
            all = organizacaoMilitarService.getAll(size,page);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
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
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody OrganizacaoMilitar om){
        organizacaoMilitarService.update(id, om);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado com sucesso"));

    }

}
