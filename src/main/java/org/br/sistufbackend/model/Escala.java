package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "escala")
public class Escala {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="escala_sequence")
    @SequenceGenerator(name="escala_sequence", sequenceName="escala_idescala_seq",
            allocationSize = 1)
    @Column(name = "idescala")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "roteiro_idroteiro")
    private Roteiro roteiro;
    @ManyToOne
    @JoinColumn(name = "agencia_idagencia")
    private Agencia agencia;
    @ManyToOne
    @JoinColumn(name = "porto_idporto")
    private Porto porto;
    @ManyToOne
    @JoinColumn(name = "razao_visita_idrazao_visita")
    private RazaoDeVisita razaoDeVisita;
    @ManyToOne
    @JoinColumn(name = "categoria_visita_idcategoria_visita")
    private CategoriaVisita categoriaVisita;
    @Column(name = "datahora_chegada")
    LocalDateTime chegada;
    @Column(name = "datahora_saida")
    LocalDateTime saida;
    @Column(name = "numero_duv")
    private Integer numeroDuv;





}
