package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.EstadoService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estado")
@CrossOrigin(origins = "*")
public class EstadoController {
    @Autowired
    EstadoService estadoService;
    @Autowired
    HeaderService headerService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    LogService logService;
    @PostMapping
    public ResponseEntity create(@RequestBody  Estado estado){
        Estado saved = estadoService.save(estado);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao("UC-ESTADO")
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<Estado> all = estadoService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = estadoService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));


        if(nome != null && !nome.isEmpty()){
            return ResponseEntity.ok(estadoService.findAllByName(nome));
        }
        return ResponseEntity.ok().headers(customHeaders).body(estadoService.getAll(size,page));
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        try{
            Estado byId = estadoService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(byId);
        }catch(EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Messagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        try{
            Estado estado = estadoService.getById(id).get();
            estadoService.deleteById(id);
            String ipFromRequest = networkService.getIpFromRequest(request);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Log log = Log.builder()
                    .acao(LogAction.DELETE)
                    .descricao(estado.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao("UC-ESTADO")
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            return ResponseEntity.ok(Map.of("Mensage", "Sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Esse Estado esta sendo utilizado em algum recurso e não pode ser deletado."));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable String id, @RequestBody Estado estado){
        Estado estadoDoBanco = estadoService.getById(id).get();
        estadoService.update(id, estado);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("Estado Original: " + estadoDoBanco.toString() + " Estado Modificado: " + estado.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao("UC-ESTADO")
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensage", "Sucesso"));
    }
}
