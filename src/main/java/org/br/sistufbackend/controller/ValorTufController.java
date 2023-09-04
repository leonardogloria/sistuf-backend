package org.br.sistufbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.exception.TonelagemValidationException;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.ValorTUF;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.ValorTufService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/valorTuf")
@CrossOrigin(origins = "*")
public class ValorTufController {
    private final String UC_VALOR_TUF = "UC_VALOR_TUF";
    @Autowired
    ValorTufService valorTufService;
    @Autowired
    HeaderService headerService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping
    public ResponseEntity getAll(){
        Long count = valorTufService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        List<ValorTUF> all = valorTufService.getAll();
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @PostMapping
    public ResponseEntity create(@RequestBody ValorTUF valorTUF){
        try{
            ValorTUF saved = valorTufService.create(valorTUF);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String ipFromRequest = networkService.getIpFromRequest(request);
            Log log = Log.builder()
                    .acao(LogAction.INSERT)
                    .descricao(saved.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_VALOR_TUF)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (TonelagemValidationException ex){
            System.out.println(ex.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    ex.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable  Long id){
        try{
            ValorTUF deletado = valorTufService.getById(id);
            valorTufService.deleteById(id);
            String ipFromRequest = networkService.getIpFromRequest(request);
            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Log log = Log.builder()
                    .acao(LogAction.DELETE)
                    .descricao(deletado.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_VALOR_TUF)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
        }catch (DataIntegrityViolationException ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Mensagem",
                    "Essa Regra esta sendo utilizada em algum recurso e não pode ser deletada."));
        }
    }

}
