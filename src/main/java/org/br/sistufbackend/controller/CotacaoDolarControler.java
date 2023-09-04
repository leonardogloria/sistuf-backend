package org.br.sistufbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.CotacaoDolar;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.RazaoDeVisita;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.CotacaoDolarService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cotacaoDolar")
@CrossOrigin(origins = "*")
public class CotacaoDolarControler {
    private final String UC_COTACAO_DOLAR = "UC_COTACAO_DOLAR";
    @Autowired
    CotacaoDolarService cotacaoDolarService;
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
        Long count = cotacaoDolarService.count();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("total-size",count.toString());
        httpHeaders.set("total-size",count.toString());
        httpHeaders.setAccessControlExposeHeaders(List.of("*"));
        List<CotacaoDolar> all;
        if(nome != null && !nome.isEmpty()){
            all = null;
        }else{
            all =  cotacaoDolarService.getAll(size,page);
        }
        return ResponseEntity.ok().headers(httpHeaders).body(all);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CotacaoDolar cotacaoDolar){
        CotacaoDolar saved = cotacaoDolarService.save(cotacaoDolar);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_COTACAO_DOLAR)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable LocalDate id){
        CotacaoDolar deletado = cotacaoDolarService.getById(id).get();
        cotacaoDolarService.deleteById(id);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletado.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_COTACAO_DOLAR)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
}
