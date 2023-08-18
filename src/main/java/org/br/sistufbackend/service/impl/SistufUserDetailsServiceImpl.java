package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.repository.SecUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SistufUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SecUserRepository secUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.secUserRepository.findByLoginOrCpfOrNip(username).
                orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado."));
    }
}
