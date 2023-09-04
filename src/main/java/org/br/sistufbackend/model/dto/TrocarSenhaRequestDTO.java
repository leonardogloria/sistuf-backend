package org.br.sistufbackend.model.dto;

import lombok.Data;

@Data
public class TrocarSenhaRequestDTO {

    private String senhaAtual;
    private String senhaNova;
    private String confirmacao;
    private boolean ignorarAtual = false;

}
