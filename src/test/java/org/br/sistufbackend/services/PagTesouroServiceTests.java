package org.br.sistufbackend.services;

import org.br.sistufbackend.model.dto.CobrancaDTO;
import org.br.sistufbackend.model.payload.PagTesouroResponsePayload;
import org.br.sistufbackend.model.enums.StatusPagTesouro;
import org.br.sistufbackend.service.PagTesouroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(value = "test")
public class PagTesouroServiceTests {
    @Autowired
    PagTesouroService pagTesouroService;
    @Test
    @DisplayName("Testa a criação de um pagamento")
    public void testaCriacaoDePagamento(){
        CobrancaDTO cobranca = CobrancaDTO.builder().referencia("123456").vencimento("14122023")
                .nomeContribuinte("leonardo")
                .valorPrincipal(new BigDecimal("2000")).build();
        PagTesouroResponsePayload pagTesouroResponsePayload = pagTesouroService.gerarCobranca(cobranca);
        //System.out.println(pagTesouroResponsePayload);
        assertEquals(StatusPagTesouro.CRIADO,pagTesouroResponsePayload.getSituacao().getCodigo());

    }
    @Test
    @DisplayName("Verifica Status do pagamento")
    public void verificaStatusDOPagamento(){
        CobrancaDTO cobranca = CobrancaDTO.builder().referencia("123456").vencimento("14122023")
                .nomeContribuinte("leonardo")
                .valorPrincipal(new BigDecimal("2000")).build();
        PagTesouroResponsePayload pagTesouroResponsePayload = pagTesouroService.gerarCobranca(cobranca);
        PagTesouroResponsePayload response = pagTesouroService.verificaStatusPagamento(pagTesouroResponsePayload.getIdPagamento());
        assertEquals(StatusPagTesouro.CRIADO, response.getSituacao().getCodigo());

    }

}
