package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
public class Pais {

    @Id
    @Column(name = "idpais")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="pais_sequence")
    @SequenceGenerator(name="pais_sequence", sequenceName="pais_idpais_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "codigo_iso2")
    private String codigoPaisAlpha2;
    @Column(name = "codigo_iso3")
    private String codigoPaisAlpha3;
    @Column(name = "nome_ptb")
    private String nome;
    @Column(name = "nome_eng")
    private String nomeEmIngles;
    @Column(name = "codigo_ddi")
    private String codigoDDI;
    @Column(name = "acordo")
    private String acordoComBrasil;

    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id +
                ", codigoPaisAlpha3='" + codigoPaisAlpha3 + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
