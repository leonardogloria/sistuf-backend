package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@Builder@NoArgsConstructor
@Entity
public class Estado {
    @Id
    private String uf;
    private String nome;
}
