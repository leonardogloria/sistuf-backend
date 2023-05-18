package org.br.sistufbackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@AllArgsConstructor @NoArgsConstructor @Builder
public class Navio {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;
    @ManyToOne
    @JoinColumn(name = "tipo_navio_id")
    private TipoNavio tipoNavio;
    private String numeroNavioIMO;
    private String irin;
    private String armador;
    private String nome;
    private Double tonelagemPesoBruto;
    private Double velocidadeCruzeiro;
    private Boolean documentacao;
    private String telefone;
    private Boolean pagaIndependenteReciprocidade;

}
