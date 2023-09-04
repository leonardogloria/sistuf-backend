package org.br.sistufbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.EscalaService;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.validation.exception.EscalaValidationException;
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
@RequestMapping("/escala")
@CrossOrigin(origins = "*")
public class EscalaController {
    private final String UC_ESCALA = "UC_ESCALA";
    @Autowired
    HeaderService headerService;
    @Autowired
    EscalaService escalaService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/roteiro/{id}")
    public ResponseEntity getAllByRoteiro(@PathVariable  Long id){
        List<Escala> allByRoteiroId = escalaService.getAllByRoteiroId(id);
        HttpHeaders customHeaders = headerService
                .getCustomHeaders(Map.of("total-size", String.valueOf(allByRoteiroId.size())));

        return ResponseEntity.ok().headers(customHeaders).body(allByRoteiroId);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Escala escala){

        try{
            Escala saved = escalaService.save(escala);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String ipFromRequest = networkService.getIpFromRequest(request);
            Log log = Log.builder()
                    .acao(LogAction.INSERT)
                    .descricao(saved.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_ESCALA)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (EscalaValidationException e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        try{
            Escala deletada = escalaService.getById(id).get();
            escalaService.deleteById(id);
            String ipFromRequest = networkService.getIpFromRequest(request);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Log log = Log.builder()
                    .acao(LogAction.DELETE)
                    .descricao(deletada.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_ESCALA)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));

        }catch (EscalaValidationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Escala escala){
        try{
            Escala original = escalaService.getById(id).get();
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String ipFromRequest = networkService.getIpFromRequest(request);

            Log log = Log.builder()
                    .acao(LogAction.UPDATE)
                    .descricao("ESCALA Original: " + original.toString() + " ESCALA Modificado: " + escala.toString() )
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_ESCALA)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            escalaService.update(id,escala);

        }catch (EscalaValidationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));

    }
}
