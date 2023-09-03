package org.br.sistufbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.br.sistufbackend.model.Log;
import org.br.sistufbackend.model.dto.LoginRequestDTO;
import org.br.sistufbackend.model.enums.LogAction;
import org.br.sistufbackend.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LogService logService;

    @PostMapping("authorize")
    public ResponseEntity doLogin(@RequestBody LoginRequestDTO requestDto, HttpServletRequest request, HttpSession session) {
        Authentication authentication = null;
        session.invalidate();
        try{
            authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

            Log log = Log.builder()
                    .acao(LogAction.LOGIN_SUCESSO)
                    .descricao("")
                    .ip("127.0.0.1")
                    .username(requestDto.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao("UC-LOGIN")
                    .data(LocalDateTime.now()).build();
            logService.insert(log);

        }catch (BadCredentialsException ex){
            Log log = Log.builder()
                    .acao(LogAction.LOGIN_NAO_REALIZADO)
                    .descricao("Login não realizado (BAD CREDENTIALS) com usuario: " + requestDto.getUsername())
                    .ip("127.0.0.1")
                    .username(requestDto.getUsername())
                    .criador("SPRINGBOOT")
                    .aplicacao("UC-LOGIN")
                    .data(LocalDateTime.now()).build();
            logService.insert(log);
            throw new BadCredentialsException("Please enter a valid username and password.");

        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // Create a new session and add the security context.
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @PostMapping("deauthorize")
    public ResponseEntity doLogout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

}
