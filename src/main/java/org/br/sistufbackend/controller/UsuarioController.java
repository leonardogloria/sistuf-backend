package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.br.sistufbackend.exception.UsuarioValidationException;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final String UC_USUARIOS = "UC_USUARIOS";

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    HeaderService headerService;
    @Autowired
    AgenciaService agenciaService;
    @Autowired
    OrganizacaoMilitarService organizacaoMilitarService;
    @Autowired
    LogService logService;
    @Autowired
    NetworkService networkService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false)  String nome,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "false") Boolean buscaPorAgencia,
                                 @RequestParam(required = false, defaultValue = "false") Boolean buscaPorCapitania){
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(buscaPorCapitania){
            Long orgMilId = organizacaoMilitarService.findOrganizacaoDoUsuarioById(authentication.getId());
            List<Usuario> allUsuariosByOrgMilId = organizacaoMilitarService.findAllUsuariosByOrgMilId(orgMilId);
            Integer count =  allUsuariosByOrgMilId.size();
            HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
            return ResponseEntity.ok().headers(customHeaders).body(allUsuariosByOrgMilId);

        }else if(buscaPorAgencia){
            Long agenciaDoUsuarioById = agenciaService.findAgenciaDoUsuarioById(authentication.getId());
            List<Usuario> allByAgenciaId = agenciaService.findAllByAgenciaId(agenciaDoUsuarioById);
            Integer count =  allByAgenciaId.size();
            HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
            return ResponseEntity.ok().headers(customHeaders).body(allByAgenciaId);

        }else{
            Long count = usuarioService.count();
            HttpHeaders customHeaders = headerService.getCustomHeaders(Map.of("total-size", count.toString()));
            List<Usuario> all ;
            if(nome != null && !nome.isEmpty()){
                all = usuarioService.findByKeys(nome);
                //all = usuarioService.getAll();
            }else{
                all = usuarioService.getAll(page,size);
            }
            return ResponseEntity.ok().headers(customHeaders).body(all);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity getByID(@PathVariable String id){
        try {
            Usuario usuario = usuarioService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        Usuario deletado = usuarioService.getById(id).get();
        String ipFromRequest = networkService.getIpFromRequest(request);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Log log = Log.builder()
                .acao(LogAction.DELETE)
                .descricao(deletado.toString())
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_USUARIOS)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        usuarioService.deleteById(id);
        return ResponseEntity.ok(Map.of("Mensagem", "Deletado com sucesso"));
    }
    @PostMapping
    public ResponseEntity create(@RequestBody Usuario usuario,
                                @RequestParam(required = false, defaultValue = "false") Boolean isInAgencia){
        try{
            Usuario saved = usuarioService.save(usuario);

            SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String ipFromRequest = networkService.getIpFromRequest(request);
            Log log = Log.builder()
                    .acao(LogAction.INSERT)
                    .descricao(saved.toString())
                    .ip(ipFromRequest)
                    .username(authentication.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao(UC_USUARIOS)
                    .data(LocalDateTime.now()).build();
            logService.insert(log);

            if(isInAgencia){
                authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Long agenciaDoUsuarioById = agenciaService.findAgenciaDoUsuarioById(authentication.getId());
                try{
                    agenciaService.associaUsuarioAAgencia(saved.getId(),agenciaDoUsuarioById);
                    ipFromRequest = networkService.getIpFromRequest(request);
                    Log logAgencia = Log.builder()
                            .acao(LogAction.INSERT)
                            .descricao(saved.toString())
                            .ip(ipFromRequest)
                            .username(authentication.getUsername())
                            .criador("SPRINGBOOT")
                            .aplicacao(UC_USUARIOS)
                            .descricao("USUARIO: " + saved.getId() + " INSERIDO NA AGENCIA: " + agenciaDoUsuarioById )
                            .data(LocalDateTime.now()).build();
                    logService.insert(logAgencia);
                }catch (EmptyResultDataAccessException ignore){}

            }
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);



        }catch (UsuarioValidationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody Usuario usuario){
        Usuario original = usuarioService.getById(id).get();
        usuarioService.update(id,usuario);
        SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipFromRequest = networkService.getIpFromRequest(request);

        Log log = Log.builder()
                .acao(LogAction.UPDATE)
                .descricao("USUARIO Original: " + original.toString() + " USUARIO Modificado: " + usuario.toString() )
                .ip(ipFromRequest)
                .username(authentication.getUsername())
                .criador("SPRINGBOOT")
                .aplicacao(UC_USUARIOS)
                .data(LocalDateTime.now()).build();
        logService.insert(log);
        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }
    @PutMapping("/associarCapitania/{cpf}")
    public ResponseEntity associarCapitania(@PathVariable String cpf){
        Optional<Usuario> userOptional = usuarioService.findByCpf(cpf);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", "CPF NÃO EXISTENTE!"));
        }else if (agenciaService.isUsuarioInAgencia(userOptional.get().getId())){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message", "USUARIO PERTENCE A UMA AGENCIA!"));
        }else {

            if(organizacaoMilitarService.isUsuarioInOrgMil(userOptional.get().getId())){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("Message", "USUARIO JA PERTENCE A UMA CAPITANIA!"));
            }else {
                SecUsuario authentication = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Long orgMilId = organizacaoMilitarService.findOrganizacaoDoUsuarioById(authentication.getId());
                organizacaoMilitarService.associarUsuarioAOrganizacao(userOptional.get().getId(),orgMilId);
                String ipFromRequest = networkService.getIpFromRequest(request);

                Log log = Log.builder()
                        .acao(LogAction.INSERT)
                        .descricao("USUARIO: " + userOptional.get().getId() + "Associado a Capitania: " + orgMilId)
                        .ip(ipFromRequest)
                        .username(authentication.getUsername())
                        .criador("SPRINGBOOT")
                        .aplicacao(UC_USUARIOS)
                        .data(LocalDateTime.now()).build();
                logService.insert(log);
            }
        }

        return ResponseEntity.ok(Map.of("Mensagem", "Atualizado Com sucesso"));
    }



}
