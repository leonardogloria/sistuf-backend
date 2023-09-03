package org.br.sistufbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "sec_users")
public class Usuario {
    @Id
    @Column(name = "login")
    private String id;


    @Column(name = "pswd")
    private String senha;
    @Column(name = "name")
    private String nome;
    @Column(name = "email")
    private String email;
    @Column(name = "active")
    private char isAtivo = 'Y';
    @Column(name = "activation_code")
    private String codigoAtivacao;
    @Column(name = "priv_admin")
    private char isAdmin = 'Y';
    //@Column(name = "tipo_usr")
    //private String tipoUsuario ='0';
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nip")
    private String nip;

    /*
     login           varchar(32) default ''::character varying  not null
        primary key,
    pswd            varchar(32) default ''::character varying  not null,
    name            varchar(64) default ''::character varying  not null,
    email           varchar(64),
    active          varchar(1)  default 'Y'::character varying not null,
    activation_code varchar(32),
    priv_admin      varchar(1),
    tipo_usr        char        default 'O'::bpchar            not null,
    cpf             varchar(11) default ''::character varying  not null,
    nip             varchar(8)
     */


}
