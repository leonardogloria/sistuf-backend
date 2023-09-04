package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.CategoriaVisita;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.CategoriaVisitaService;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categoriaVisita")

public class CategoriaVisitaController {
    private final String CATEGORIA_VISITA="UC_CATEGORIA_VISITA";
    @Autowired
    CategoriaVisitaService categoriaVisitaService;
    @Autowired
    HeaderService headerService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<CategoriaVisita> all = categoriaVisitaService.getAll();
        return ResponseEntity.ok(all);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody  CategoriaVisita categoriaVisita){
        CategoriaVisita saved = categoriaVisitaService.save(categoriaVisita);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(CATEGORIA_VISITA)
                .data(LocalDateTime.now()).build();
        logService.insert(log);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try {
            CategoriaVisita categoriaVisita = categoriaVisitaService.getById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return ok(categoriaVisita);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        List<CategoriaVisita> all = new ArrayList<>();
        Long count = categoriaVisitaService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));



        if(nome != null && !nome.isEmpty()){
            all = categoriaVisitaService.findByDescricaoDetalhada(nome,size);
        }else{
            all = categoriaVisitaService.getAll(size,page);
        }

        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try{
            CategoriaVisita categoriaVisita = categoriaVisitaService.getById(id).get();
            categoriaVisitaService.deleteById(id);
            String ipFromRequest = networkService.getIpFromRequest(request);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Log log = Log.builder()
                    .acao(LogAction.DELETE)
                    .descricao(categoriaVisita.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(CATEGORIA_VISITA)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);

            return ok(Map.of("Mensagem", "Deletado com sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Essa Categoria esta sendo utilizada em algum recurso e não pode ser deletada."));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody CategoriaVisita categoriaVisita){
        CategoriaVisita original = categoriaVisitaService.getById(id).get();
        categoriaVisitaService.update(id,categoriaVisita);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("Categoria Original: " + original.toString() + " Categoria Modificada: " + categoriaVisita.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(CATEGORIA_VISITA)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }
}
