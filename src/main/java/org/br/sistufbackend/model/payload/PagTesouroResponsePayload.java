package org.br.sistufbackend.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.sistufbackend.model.enums.TipoPagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data@Builder@AllArgsConstructor@NoArgsConstructor
public class PagTesouroResponsePayload {
    private String idPagamento;
    private LocalDateTime dataCriacao;
    private String proximaUrl;
    private Situacao situacao;
    private TipoPagamento tipoPagamentoEscolhido;

}
