package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.Pais;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.PaisService;
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
@RequestMapping("/pais")
@CrossOrigin(origins = "*")
public class PaisController {
    @Autowired
    PaisService paisService;
    @Autowired
    HeaderService headerService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public ResponseEntity create(@RequestBody Pais pais){
        Pais saved = paisService.save(pais);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(pais.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao("UC-PAIS")
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Pais pais = paisService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(pais);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }

    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<Pais> all = paisService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page
                                 ){
        List<Pais> paises;
        Long count = paisService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        if(nome != null && !nome.isEmpty()){
            paises = paisService.findAllByNome(nome);
        }else {
            paises = paisService.getAll(size,page);
        }
        return ResponseEntity.ok().headers(customHeaders).body(paises);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable  Long id){
        try{
            Pais pais = paisService.getById(id).get();
            paisService.deleteById(id);
            String ipFromRequest = networkService.getIpFromRequest(request);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Log log = Log.builder()
                    .acao(LogAction.DELETE)
                    .descricao(pais.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao("UC-PAIS")
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Esse País esta sendo utilizado em algum recurso e não pode ser deletado."));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody Pais pais){
        Pais paisOriginal  = paisService.getById(id).get();
        paisService.update(id,pais);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("Pais Original: " + paisOriginal.toString() + " País Modificado: " + pais.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao("UC-PAIS")
                .data(LocalDateTime.now()).build();
        logService.insert(log);

        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
