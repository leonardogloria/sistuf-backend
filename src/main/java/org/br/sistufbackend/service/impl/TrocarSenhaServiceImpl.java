package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.dto.TrocarSenhaRequestDTO;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.repository.SecUsuarioRepository;
import org.br.sistufbackend.service.TrocarSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Random;

import static org.br.sistufbackend.model.enums.YesNo.*;
import static org.springframework.util.StringUtils.containsWhitespace;
import static org.springframework.util.StringUtils.hasText;

@Service
public class TrocarSenhaServiceImpl implements TrocarSenhaService {
    @Autowired
    private SecUsuarioRepository secUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void trocarSenha(TrocarSenhaRequestDTO requestDTO) {
        if ( (!requestDTO.isIgnorarAtual() && !hasText(requestDTO.getSenhaAtual())) ||
                !hasText(requestDTO.getSenhaNova()) || !hasText(requestDTO.getConfirmacao()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Todos os campos são obrigatórios.");

        if ((!requestDTO.isIgnorarAtual() && containsWhitespace(requestDTO.getSenhaAtual())) ||
                containsWhitespace(requestDTO.getSenhaNova()) || containsWhitespace(requestDTO.getConfirmacao()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Os campos não podem conter espaços em branco.");

        if (!requestDTO.getSenhaNova().equals(requestDTO.getConfirmacao()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "As senhas devem ser iguais.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        SecUsuario secUsuario = this.secUsuarioRepository.findByLoginOrCpfOrNip(username).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Dados do usuário não encontrados."));



        if (!requestDTO.isIgnorarAtual()) {
            if (!Objects.equals(requestDTO.getSenhaAtual(), secUsuario.getPassword()) &&
                            !passwordEncoder.matches(requestDTO.getSenhaAtual(), secUsuario.getPassword()))
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Senha atual informada não é igual a salva para o usuário.");
        }

        secUsuario.setSenha(passwordEncoder.encode(requestDTO.getSenhaNova()));
        secUsuario.setChangePswd(N);

        SecUsuario loggedUser = this.secUsuarioRepository.save(secUsuario);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(loggedUser, loggedUser.getPassword(), loggedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public String resetSenha(String username) {
        SecUsuario currentUser = (SecUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getIsAdmin().isTrue())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem direito de acesso à funcionalidade.");


        SecUsuario secUsuario = this.secUsuarioRepository.findById(username).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Dados do usuário não encontrados."));

        secUsuario.setChangePswd(Y);
        String randomPswd = this.generateRandomPswd();
        secUsuario.setSenha(this.passwordEncoder.encode(randomPswd));
        this.secUsuarioRepository.save(secUsuario);

        return randomPswd;
    }

    private String generateRandomPswd() {
        // Utilizada solução do tutorial https://www.baeldung.com/java-random-string

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
