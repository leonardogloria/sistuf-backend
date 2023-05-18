package org.br.sistufbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
public class Pais {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String codigoPaisAlpha2;
    private String codigoPaisAlpha3;
    private String nome;
    private String nomeEmIngles;
    private String codigoDDI;
    private boolean acordoComBrasil;
}
