package org.br.sistufbackend.service;

import org.br.sistufbackend.model.dto.TrocarSenhaRequestDTO;

public interface TrocarSenhaService {
    void trocarSenha(TrocarSenhaRequestDTO requestDTO);

    String resetSenha(String username);
}
