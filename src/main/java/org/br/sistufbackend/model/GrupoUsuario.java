package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sec_groups")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class GrupoUsuario {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sec_group_sequence")
    @SequenceGenerator(name="sec_group_sequence", sequenceName="sec_groups_group_id_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "description")
    private String descricao;
    @Column(name = "visivel_capitania")
    private char isVisivelCapitania;
    @Column(name = "visivel_agencia")
    private char isVisivelAgencia;
}
