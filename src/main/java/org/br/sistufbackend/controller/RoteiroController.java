package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roteiro")
@CrossOrigin(origins = "*")
public class RoteiroController {
    private final String UC_ROTEIRO = "UC_ROTEIRO";
    @Autowired
    RoteiroService roteiroService;
    @Autowired
    HeaderService headerService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = roteiroService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<Roteiro> all ;
        if(nome != null && !nome.isEmpty()){
            all = roteiroService.getAll();
        }else{
            all = roteiroService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            Roteiro roteiro = roteiroService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(roteiro);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        Roteiro deletado = roteiroService.getById(id).get();
        roteiroService.deleteById(id);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletado.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_ROTEIRO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Roteiro roteiro){
        Roteiro saved = roteiroService.save(roteiro);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_ROTEIRO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Roteiro roteiro){
        Roteiro original = roteiroService.getById(id).get();
        roteiroService.update(id,roteiro);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("Roteiro Original: " + original + " Roteiro Modificado: " + roteiro.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_ROTEIRO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));

    }




}
