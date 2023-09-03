package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.RazaoDeVisita;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.RazaoDeVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/razaoVisita")
@CrossOrigin(origins = "*")
public class RazaoVisitaController {
    private final String RAZAO_VISITA_UC = "UC_RAZAO_VISITA";
    @Autowired
    RazaoDeVisitaService razaoDeVisitaService;
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
        List<RazaoDeVisita> all = razaoDeVisitaService.getAll();
        return ResponseEntity.ok(all);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody RazaoDeVisita razaoDeVisita){
        RazaoDeVisita saved = razaoDeVisitaService.create(razaoDeVisita);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(RAZAO_VISITA_UC)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){

        Long count = razaoDeVisitaService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));

        List<RazaoDeVisita> all;

        if(nome != null && !nome.isEmpty()){
            all = razaoDeVisitaService.findAllByRotulo(nome);
        }else{
            all =  razaoDeVisitaService.getAll(size,page);
        }

        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable  Long id){
        try {
            RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(razaoDeVisita);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        RazaoDeVisita deletado = razaoDeVisitaService.getById(id).get();
        razaoDeVisitaService.deleteById(id);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletado.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(RAZAO_VISITA_UC)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody RazaoDeVisita razaoDeVisita){
        RazaoDeVisita original = razaoDeVisitaService.getById(id).get();
        razaoDeVisitaService.update(id,razaoDeVisita);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("Razao Original: " + original.toString() + " Razao Modificado: " + razaoDeVisita.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(RAZAO_VISITA_UC)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));


    }
}
