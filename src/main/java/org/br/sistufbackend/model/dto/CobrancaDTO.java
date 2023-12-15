package org.br.sistufbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class CobrancaDTO {
    private String codigoServico;
    private String referencia;
    private String competencia;
    private String vencimento;
    private String cnpjCpf;
    private String nomeContribuinte;
    private BigDecimal valorPrincipal;
    private String valorDescontos;
    private String valorOutrasDeducoes;
    private String valorMulta;
    private String valorJuros;
    private String valorOutrosAcrescimos;
    private String urlRetorno;
    private String urlNotificacao;
    private String modoNavegacao;
    private String expiracaoPix;



}
