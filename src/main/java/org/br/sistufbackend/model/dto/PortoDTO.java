package org.br.sistufbackend.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@Builder
public class PortoDTO {
    private Long id;
    private String nome;

    public PortoDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
