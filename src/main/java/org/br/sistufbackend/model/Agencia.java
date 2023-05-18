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
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String cnpj;
    private String nome;
    private String email;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
    private String cidade;
    private String bairro;
    private String telefone;
    private String ramal;
    private String contato;
    private StatusAgencia status;


}
