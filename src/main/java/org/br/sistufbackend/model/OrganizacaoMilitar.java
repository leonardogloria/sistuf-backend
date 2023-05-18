package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor@NoArgsConstructor@Builder@Data
public class OrganizacaoMilitar {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String cnpj;
    private String codigo;
    private String nome;
    private String indicativo;
    private String sigla;
    private String email;
    private String telefoneExterno;
    private String sistelma;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
    private String cidade;
    private String bairro;

}
