package org.br.sistufbackend.services;

import org.br.sistufbackend.model.ValorTUF;
import org.br.sistufbackend.service.ValorTufService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(value = "test")
public class ValorTufServiceTests {
    @Autowired
    ValorTufService valorTufService;

    @Test@DisplayName("Deve retornar em qual TUF a tonelagem se encontra")
    public void testaValorTuf(){
        ValorTUF valorTUF = valorTufService.getByTonelagem(5000);
        assertEquals(2,valorTUF.getId());
        valorTUF = valorTufService.getByTonelagem(60000);
        assertEquals(4,valorTUF.getId());
        valorTUF = valorTufService.getByTonelagem(150_000);
        assertEquals(5,valorTUF.getId());
    }

}
