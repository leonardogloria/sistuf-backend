package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
@Table(name = "roteiro")
public class Roteiro {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="roteiro_sequence")
    @SequenceGenerator(name="roteiro_sequence", sequenceName="roteiro_idroteiro_seq",
            allocationSize = 1)
    @Column(name = "idroteiro")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "navio_idnavio")
    private Navio navio;
    @Column(name = "datahora_entrada")
    private LocalDateTime entrada;
    @Column(name = "datahora_saida")
    private LocalDateTime saida;
    @ManyToOne
    @JoinColumn(name = "porto_idporto_origem")
    private Porto portoOrigem;
    @ManyToOne
    @JoinColumn(name = "porto_idporto_destino")
    private Porto portoDestino;
    @Column(name = "qtd_portos")
    private Integer quantidadePortos;

}
