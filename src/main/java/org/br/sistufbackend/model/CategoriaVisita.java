package org.br.sistufbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class CategoriaVisita {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String descricaoDetalhada;
    private String descricaoLei;
    private String descricaoAbreviada;
    private LocalDate inicioVigencia;
    private LocalDate terminoVigencia;
}
