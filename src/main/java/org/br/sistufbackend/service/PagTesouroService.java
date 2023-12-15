package org.br.sistufbackend.service;

import org.br.sistufbackend.model.dto.CobrancaDTO;
import org.br.sistufbackend.model.payload.PagTesouroResponsePayload;

public interface PagTesouroService {
    PagTesouroResponsePayload gerarCobranca(CobrancaDTO cobrancaDTO);

    PagTesouroResponsePayload verificaStatusPagamento(String id);
}
