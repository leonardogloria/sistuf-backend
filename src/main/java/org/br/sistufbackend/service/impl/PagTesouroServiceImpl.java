package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.service.PagTesouroService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PagTesouroServiceImpl implements PagTesouroService {
    @Override
    public void gerarCobranca() {
        RestTemplate restTemplate = new RestTemplate();


    }
}
