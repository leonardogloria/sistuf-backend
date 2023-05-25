package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUF;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class RazaoDeVisita {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "rotulo")
    private String rotulo;
    @Column(name = "regra")
    private RegraDeCobrancaTUF regraDeCobrancaTUF;
    @ManyToOne
    @JoinColumn(name = "categoria_visita_idcategoria_visita")
    private CategoriaVisita categoriaVisita;
}