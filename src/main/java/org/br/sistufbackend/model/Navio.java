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
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="navio_sequence")
    @SequenceGenerator(name="navio_sequence", sequenceName="navio_idnavio_seq",
            allocationSize = 1)
    @Column(name = "idnavio")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pais_idpais")
    private Pais pais;
    @ManyToOne
    @JoinColumn(name = "tipo_navio_idtipo_navio")
    private TipoNavio tipoNavio;
    @Column(name = "numero_imo")
    private String numeroNavioIMO;
    @Column(name = "irin")
    private String irin;
    @Column(name = "armador")
    private String armador;
    @Column(name = "nome")
    private String nome;
    @Column(name = "tpb")
    private Integer tonelagemPesoBruto;
    @Column(name = "velocidade_cruzeiro")
    private String velocidadeCruzeiro ;
    @Column(name = "possui_documentacao")
    private Character documentacao;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "paga_indep_acordo")
    private Character pagaIndependenteReciprocidade;
}
