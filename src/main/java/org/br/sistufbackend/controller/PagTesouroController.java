package org.br.sistufbackend.controller;

import org.apache.coyote.Response;
import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.model.GRU;
import org.br.sistufbackend.model.dto.CobrancaDTO;
import org.br.sistufbackend.model.payload.PagTesouroResponsePayload;
import org.br.sistufbackend.service.EscalaService;
import org.br.sistufbackend.service.GruService;
import org.br.sistufbackend.service.PagTesouroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/pagTesouro")
@CrossOrigin(origins = "*")
public class PagTesouroController {
    @Autowired
    PagTesouroService pagTesouroService;
    @Autowired
    EscalaService escalaService;
    @Autowired
    GruService gruService;
    @GetMapping("/pagamento/{id}/status")
    public ResponseEntity getStatusPagamento(@PathVariable  String id){
        PagTesouroResponsePayload pagTesouroResponsePayload = pagTesouroService.verificaStatusPagamento(id);
        return ResponseEntity.ok(pagTesouroResponsePayload);
    }
    @GetMapping("/create/gru/{id}")
    public void criarUrlPagamentoByGru(@PathVariable  Long id){
        GRU gru = gruService.getById(id).get();
        gru.setEmissao(LocalDate.now());

        CobrancaDTO cobranca = CobrancaDTO.builder().referencia(gru.getId().toString())
                .vencimento(gru.getVencimento().format(DateTimeFormatter.ofPattern("ddMMyyyy")))
                .nomeContribuinte("PAGAMENTO DE TUF")
                .valorPrincipal(gru.getValorReal()).build();
        PagTesouroResponsePayload pagTesouroResponse = pagTesouroService.gerarCobranca(cobranca);
        gru.setDpcProximaURL(pagTesouroResponse.getProximaUrl());
        gru.setIdPagTesouro(pagTesouroResponse.getIdPagamento());
        gru.setPagTesouroStatus(pagTesouroResponse.getSituacao().getCodigo().name());
        gruService.update(gru);

    }
    @GetMapping("/create/escala/{id}")
    public void criarUrlPagamentoByEscala(@PathVariable  Long id){
        //GRU gru = gruService.getById(id).get();
        Escala escala = escalaService.getById(id).get();
        GRU gru = gruService.findByEscalaId(escala.getId()).get();

        gru.setEmissao(LocalDate.now());
        gru.setVencimento(LocalDate.now().plusDays(15));

        CobrancaDTO cobranca = CobrancaDTO.builder().referencia(gru.getId().toString())
                .vencimento(gru.getVencimento().format(DateTimeFormatter.ofPattern("ddMMyyyy")))
                .nomeContribuinte("PAGAMENTO DE TUF")
                .valorPrincipal(gru.getValorReal()).build();
        PagTesouroResponsePayload pagTesouroResponse = pagTesouroService.gerarCobranca(cobranca);
        gru.setDpcProximaURL(pagTesouroResponse.getProximaUrl());
        gru.setIdPagTesouro(pagTesouroResponse.getIdPagamento());
        gru.setPagTesouroStatus(pagTesouroResponse.getSituacao().getCodigo().name());
        gruService.update(gru);

    }
}
