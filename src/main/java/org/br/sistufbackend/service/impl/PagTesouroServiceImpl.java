package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.dto.CobrancaDTO;
import org.br.sistufbackend.model.payload.PagTesouroResponsePayload;
import org.br.sistufbackend.service.PagTesouroService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PagTesouroServiceImpl implements PagTesouroService {
    @Value("${pagtesouro.url}")
    private String url;
    @Value("${pagtesouro.urlVerificacaoPagamento}")
    private String urlVerificacao;
    @Value("${pagtesouro.token}")
    private String tokenAcesso;
    @Value("${pagtesouro.codigoServico}")
    private String codigoServico;
    @Value("${pagtesouro.modoNavegacao}")
    private String modoNavegacao;
    @Override
    public PagTesouroResponsePayload gerarCobranca(CobrancaDTO cobrancaDTO) {
        cobrancaDTO.setCodigoServico(codigoServico);
        cobrancaDTO.setModoNavegacao(modoNavegacao);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenAcesso);

        HttpEntity<CobrancaDTO> requestEntity =
                new HttpEntity<>(cobrancaDTO, headers);

        return restTemplate.postForObject(url, requestEntity ,PagTesouroResponsePayload.class);
    }

    @Override
    public PagTesouroResponsePayload verificaStatusPagamento(String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenAcesso);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String url = urlVerificacao + "/" + id;
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, PagTesouroResponsePayload.class).getBody();
    }
}
