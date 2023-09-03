package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.HeaderService;
import org.br.sistufbackend.service.LogService;
import org.br.sistufbackend.service.NetworkService;
import org.br.sistufbackend.service.OrganizacaoMilitarService;
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
@RequestMapping("/organizacaoMilitar")
@CrossOrigin(origins = "*")
public class OrganizacaoMilitarController {
    private final String UC_ORGANIZACAO_MILITAR = "UC_ORGANIZACAO_MILITAR";

    @Autowired
    OrganizacaoMilitarService organizacaoMilitarService;
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
        List<OrganizacaoMilitar> all = organizacaoMilitarService.getAll();
        return ResponseEntity.ok(all);
    }
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page){
        List<OrganizacaoMilitar> all;
        Long count = organizacaoMilitarService.count();
        HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
        if(nome != null && !nome.isEmpty()){
            all = organizacaoMilitarService.findByNome(nome);
        }else{
            all = organizacaoMilitarService.getAll(size,page);
        }
        return ResponseEntity.ok().headers(customHeaders).body(all);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try {
            OrganizacaoMilitar organizacaoMilitar = organizacaoMilitarService.getById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(organizacaoMilitar);

        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @PostMapping
    public ResponseEntity create(@RequestBody OrganizacaoMilitar om){
        OrganizacaoMilitar saved = organizacaoMilitarService.save(om);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);
        Log log = Log.builder()
                .acao(LogAction.INSERT)
                .descricao(saved.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_ORGANIZACAO_MILITAR)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        OrganizacaoMilitar deletada = organizacaoMilitarService.getById(id).get();
        organizacaoMilitarService.deleteById(id);
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletada.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_ORGANIZACAO_MILITAR)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody OrganizacaoMilitar om){
        OrganizacaoMilitar original = organizacaoMilitarService.getById(id).get();
        organizacaoMilitarService.update(id, om);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("OM Original: " + original.toString() + " OM Modificado: " + om.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_ORGANIZACAO_MILITAR)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado com sucesso"));

    }

}
