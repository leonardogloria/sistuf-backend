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
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "organizacao_militar_id")
    private OrganizacaoMilitar organizacaoMilitar;
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;
    private String codigoONU;
    private String codigoDPC;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
}
