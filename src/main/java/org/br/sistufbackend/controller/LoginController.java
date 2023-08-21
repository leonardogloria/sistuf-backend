package org.br.sistufbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.br.sistufbackend.model.dto.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("authorize")
    public ResponseEntity doLogin(@RequestBody LoginRequestDTO requestDto, HttpServletRequest request, HttpSession session) {

        session.invalidate();

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

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
