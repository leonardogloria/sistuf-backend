package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUF;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUFNavio;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Table(name = "tipo_navio")
public class TipoNavio {
    @Id
    @Column(name = "idtipo_navio")

    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="tipo_navio_sequence")
    @SequenceGenerator(name="tipo_navio_sequence", sequenceName="tipo_navio_idtipo_navio_seq",
            allocationSize = 1)
    private Long id;
    @Column(name = "nome")
    private String tipoDeNavio;
    @Column(name = "regra")
    private RegraDeCobrancaTUFNavio regraDeCobrancaTUF;
    @ManyToOne
    @JoinColumn(name = "categoria_visita_idcategoria_visita")
    private CategoriaVisita categoriaVisita;
}
