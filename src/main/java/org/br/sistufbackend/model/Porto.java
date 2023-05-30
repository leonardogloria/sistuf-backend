package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class Porto {
    @Id
    @Column(name = "idporto")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="porto_sequence")
    @SequenceGenerator(name="porto_sequence", sequenceName="porto_idporto_seq",
            allocationSize = 1)
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "orgmil_idorgmil")
    private OrganizacaoMilitar organizacaoMilitar;
    @ManyToOne
    @JoinColumn(name = "pais_idpais")
    private Pais pais;
    @Column(name = "codigo_locode_unece")
    private String codigoUNE;
    @Column(name = "codigo_locode_br")
    private String codigoDPC;
    private String cep;
    @Column(name = "rua")
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    @ManyToOne
    @JoinColumn(name = "estado_uf")
    private Estado estado;
    @Column(name = "coordenadas_gps")
    private String coordenadasGps;
}
