package org.br.sistufbackend.security;

import org.br.sistufbackend.service.impl.SistufUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SistufUserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().trim();
        String password = ((String) authentication.getCredentials()).trim();
        if (username.isBlank() || password.isBlank()) {
            throw new BadCredentialsException("Todos os campos são obrigatórios.");
        }


        UserDetails user;
        try {
            user = this.userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            throw new BadCredentialsException("Usuário não encontrado.");
        }

        List<GrantedAuthority> permlist = this.getGrantedAuthorities(password, user);

        return new UsernamePasswordAuthenticationToken(user, password, permlist);
    }

    private List<GrantedAuthority> getGrantedAuthorities(String password, UserDetails user) {
        if (!Objects.equals(password, user.getPassword().trim()) &&
                !passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Informações de login inválidas.");

        if (!user.isEnabled()) {
            throw new DisabledException("Usuário desabilitado.");
        }

        if (!user.isAccountNonLocked()) {
            throw new LockedException("Conta de usuário bloqueada.");
        }

       return new ArrayList<>(user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
