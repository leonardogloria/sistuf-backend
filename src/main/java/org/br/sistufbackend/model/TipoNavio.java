package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUF;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Table(name = "tipo_navio")
public class TipoNavio {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "idtipo_navio")
    private Long id;
    @Column(name = "nome")
    private String tipoDeNavio;
    @Column(name = "regra")
    private RegraDeCobrancaTUF regraDeCobrancaTUF;
    @ManyToOne
    @JoinColumn(name = "categoria_visita_idcategoria_visita")
    private CategoriaVisita categoriaVisita;
}
