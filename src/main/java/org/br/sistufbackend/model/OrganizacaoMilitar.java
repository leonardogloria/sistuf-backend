package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor@NoArgsConstructor@Builder@Data
@Table(name = "orgmil")
public class OrganizacaoMilitar {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="om_sequence")
    @SequenceGenerator(name="om_sequence", sequenceName="orgmil_idorgmil_seq",
            allocationSize = 1)
    @Column(name = "idorgmil")
    private Long id;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "codigo_om")
    private String codigo;
    @Column(name = "nome")
    private String nome;
    @Column(name = "indicativo_naval")
    private String indicativo;
    @Column(name = "sigla")
    private String sigla;
    @Column(name = "email")
    private String email;
    @Column(name = "telefone_contato_externo")
    private String telefoneExterno;
    @Column(name = "sistelma")
    private String sistelma;
    @Column(name = "cep")
    private String cep;
    @Column(name = "rua")
    private String logradouro;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @ManyToOne
    @JoinColumn(name = "estado_uf")
    private Estado estado;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "bairro")
    private String bairro;
}
