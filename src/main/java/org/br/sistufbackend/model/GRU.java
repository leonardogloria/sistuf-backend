package org.br.sistufbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class GRU {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="gru_sequence")
    @SequenceGenerator(name="gru_sequence", sequenceName="gru_idgru_seq",
            allocationSize = 1)
    @Column(name = "idgru")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tipo_navio_idtipo_navio")
    private Escala escala;

    private LocalDate emissao;
    private LocalDate vencimento;
    @Column(name = "valor_real")
    private BigDecimal valorReal;
    private LocalDate quitacao;
    private LocalDate credito;
    private BigDecimal pago;
    private BigDecimal tarifa;
    private Character status;
    private String nossoNumero;
    @ManyToOne
    @JoinColumn(name = "valortuf_idvalortuf")
    private ValorTUF valorTUF;

    @Column(name = "pagtesouro_id_pagamento")
    private String idPagTesouro;
    @Column(name = "pagtesouro_proxima_url")
    private String pagTesouroProximaURL;
    @Column(name = "pagtesouro_status_retorno")
    private String pagTesouroStatus;
    @Column(name = "dpc_proxima_url")
    private String dpcProximaURL;
    @Column(name = "tipo_pagamento_escolhido")
    private String tipoPagamentoEscolhido;

}
