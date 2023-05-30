package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity @Table(name = "valortuf")
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ValorTUF {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cotacao_tuf_sequence")
    @SequenceGenerator(name="cotacao_tuf_sequence", sequenceName="valortuf_idvalortuf_seq",
            allocationSize = 1)
    @Column(name = "idvalortuf")
    private Long id;
    @Column(name = "tpb_inicial")
    private Long tonelagemPesoBrutoInicial;
    @Column(name = "tpb_final")
    private Long tonelagemPesoBrutoFinal;
    @Column(name = "valor_dolar")
    private BigDecimal valorDolar;
    @ManyToOne
    @JoinColumn(name = "categoria_visita_idcategoria_visita")
    private CategoriaVisita categoriaVisita;
}
