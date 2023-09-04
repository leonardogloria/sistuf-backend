package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.exception.NavioValidationException;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.dto.NavioDTO;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NavioService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.impl.HeaderServiceImpl;
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
@RequestMapping("/navio")
@CrossOrigin(origins = "*")
public class NavioController {
    private final String UC_NAVIO = "UC_NAVIO";
    @Autowired
    NavioService navioService;
    @Autowired
    private HeaderService headerService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<NavioDTO> all = navioService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        Long count = navioService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<Navio> all ;
        if(nome != null && !nome.isEmpty()){
            all = navioService.findByName(nome);
        }else{
            all = navioService.getAll(page,size);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable Long id){
        try {
            Navio navio = navioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(navio);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        Navio deletado = navioService.getById(id).get();
        navioService.deleteById(id);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletado.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_NAVIO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Navio navio){
        try{
            Navio saved = navioService.save(navio);

            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String ipFromRequest = networkService.getIpFromRequest(request);
            Log log = Log.builder()
                    .acao(LogAction.INSERT)
                    .descricao(saved.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_NAVIO)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (NavioValidationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    ex.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Navio navio){
        try{
            navioService.update(id,navio);
            Navio original = navioService.getById(id).get();
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String ipFromRequest = networkService.getIpFromRequest(request);

            Log log = Log.builder()
                    .acao(LogAction.UPDATE)
                    .descricao("NAVIO Original: " + original.toString() + " NAVIO Modificado: " + navio.toString() )
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_NAVIO)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);


            return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
        }catch (NavioValidationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    ex.getMessage()));
        }
    }
}
