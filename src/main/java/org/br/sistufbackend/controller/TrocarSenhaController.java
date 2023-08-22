package org.br.sistufbackend.controller;

import org.br.sistufbackend.model.dto.TrocarSenhaRequestDTO;
import org.br.sistufbackend.service.TrocarSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrocarSenhaController {

    @Autowired
    private TrocarSenhaService service;

    @PostMapping("/senha/alterar")
    public ResponseEntity trocarSenha(@RequestBody TrocarSenhaRequestDTO requestDTO) {
        this.service.trocarSenha(requestDTO);
        return ResponseEntity.noContent().build();
    }
}
