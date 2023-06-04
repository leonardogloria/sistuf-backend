package org.br.sistufbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@Builder
public class NavioDTO {
    private Long id;
    private String nome;

    public NavioDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
