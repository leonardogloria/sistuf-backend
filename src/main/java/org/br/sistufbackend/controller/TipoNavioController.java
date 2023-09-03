package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.TipoNavio;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.TipoNavioService;
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
import java.util.Optional;

@RestController
@RequestMapping("/tipoNavio")
@CrossOrigin(origins = "*")
public class TipoNavioController {
    private final String UC_TIPO_NAVIO = "UC_TIPO_NAVIO";
    @Autowired
    TipoNavioService navioService;
    @Autowired
    HeaderService headerService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @PostMapping
    public ResponseEntity create(@RequestBody TipoNavio navio){
        TipoNavio saved = navioService.save(navio);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_TIPO_NAVIO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/all")
    public ResponseEntity getAll(){
        List<TipoNavio> all = navioService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam(required = false)  String nome,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "0") Integer page){
        List<TipoNavio> all;
        Long count = navioService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));


        if(nome != null && !nome.isEmpty()){
            all = navioService.findAllByNome(nome);
        }else{
            all =  navioService.getAll(size, page);
        }



        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public  ResponseEntity findById(@PathVariable  Long id){
        try{
            TipoNavio navio = navioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(navio);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        TipoNavio deletado = navioService.getById(id).get();
        navioService.deleteById(id);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletado.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_TIPO_NAVIO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable  Long id,@RequestBody TipoNavio navio){
        Optional<TipoNavio> original = navioService.getById(id);
        navioService.update(id, navio);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("Tipo Navio Original: " + original.toString() + " Tipo Navio Modificado: " + navio.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_TIPO_NAVIO)
                .data(LocalDateTime.now()).build();
        logService.insert(log);

        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }

}
