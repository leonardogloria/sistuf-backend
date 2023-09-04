package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.StatusAgencia;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class Agencia {
    @Id
    @Column(name = "idagencia")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="agencia_sequence")
    @SequenceGenerator(name="agencia_sequence", sequenceName="agencia_idagencia_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "cep")
    private String cep;
    @Column(name = "rua")
    private String logradouro;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "estado_uf")
    private String estado;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "ramal")
    private String ramal;
    @Column(name = "nome_pessoa_contato")
    private String contato;
    @Column(name = "status_disponibilidade")
    private Integer status;


}
