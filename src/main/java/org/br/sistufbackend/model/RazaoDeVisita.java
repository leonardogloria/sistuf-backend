package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUF;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Table(name = "razao_visita")
public class RazaoDeVisita {
    @Id
    @Column(name = "idrazao_visita")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="razao_visita_sequence")
    @SequenceGenerator(name="razao_visita_sequence", sequenceName="razao_visita_idrazao_visita_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "rotulo")
    private String rotulo;
    @Column(name = "regra")
    private RegraDeCobrancaTUF regraDeCobrancaTUF;
    @ManyToOne
    @JoinColumn(name = "categoria_visita_idcategoria_visita")
    private CategoriaVisita categoriaVisita;
}
