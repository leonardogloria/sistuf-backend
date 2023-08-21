package org.br.sistufbackend.model.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String username;
    private String password;
}
