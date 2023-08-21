package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.repository.SecUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SistufUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SecUsuarioRepository secUsuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.secUsuarioRepository.findByLoginOrCpfOrNip(username).
                orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado."));
    }
}
