package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.dto.TrocarSenhaRequestDTO;
import org.br.sistufbackend.model.security.SecUsuario;
import org.br.sistufbackend.repository.SecUsuarioRepository;
import org.br.sistufbackend.service.TrocarSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.util.StringUtils.containsWhitespace;
import static org.springframework.util.StringUtils.hasText;

@Service
public class TrocarSenhaServiceImpl implements TrocarSenhaService {
    @Autowired
    private SecUsuarioRepository secUsuarioRepository;

    @Override
    public void trocarSenha(TrocarSenhaRequestDTO requestDTO) {
        if (!hasText(requestDTO.getSenhaAtual()) || !hasText(requestDTO.getSenhaNova()) || !hasText(requestDTO.getConfirmacao()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Todos os campos são obrigatórios.");

        if (containsWhitespace(requestDTO.getSenhaAtual()) || containsWhitespace(requestDTO.getSenhaNova()) || containsWhitespace(requestDTO.getConfirmacao()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Os campos não podem conter espaços em branco.");

        if (!requestDTO.getSenhaNova().equals(requestDTO.getConfirmacao()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "As senhas devem ser iguais.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        SecUsuario secUsuario = this.secUsuarioRepository.findByLoginOrCpfOrNip(username).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Dados do usuário não encontrados."));

        if (!requestDTO.getSenhaAtual().equals(secUsuario.getPswd()))
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Senha atual não confere com a salva para o usuário.");

        secUsuario.setPswd(requestDTO.getSenhaNova());

        this.secUsuarioRepository.save(secUsuario);
    }
}
