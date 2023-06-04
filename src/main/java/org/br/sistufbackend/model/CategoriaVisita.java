package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class CategoriaVisita {
    @Id
    //
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="categoria_visita_sequence")
    @SequenceGenerator(name="categoria_visita_sequence", sequenceName="categoria_visita_idcategoria_visita_seq",
            allocationSize = 1)
    @Column(name = "idcategoria_visita")
    private Long id;
    @Column(name = "desc_detalhada")
    private String descricaoDetalhada;
    @Column(name = "desc_lei")
    private String descricaoLei;
    @Column(name = "desc_abreviada")
    private String descricaoAbreviada;
    @Column(name = "data_inicio_vigencia")
    private LocalDate inicioVigencia;
    @Column(name = "data_termino_vigencia")
    private LocalDate terminoVigencia;
}
