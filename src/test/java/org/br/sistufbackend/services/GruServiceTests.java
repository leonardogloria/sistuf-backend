package org.br.sistufbackend.services;

import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.model.GRU;
import org.br.sistufbackend.model.payload.GRUFilters;
import org.br.sistufbackend.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles(value = "test")
public class GruServiceTests {
    @Autowired
    GruService gruService;
    @Autowired
    EscalaService escalaService;
    @Autowired
    NavioService navioService;
    @Autowired
    RoteiroService roteiroService;
    @Autowired
    ValorTufService valorTufService;

    @Test@DisplayName("Deve Inserir uma GRU no sistema")
    public void testaCriarGru(){
        Escala escala = escalaService.getById(322916L).get();

        GRU gru = new GRU();
        gru.setEscala(escala);
        gru.setValorReal(new BigDecimal("4300.20"));
        gru.setEmissao(LocalDate.now());
        assertDoesNotThrow(() -> {
            GRU created = gruService.criarComPagTesouro(gru);
            //GRU created = gruService.save(gru);
            assertNotNull(created);
            //gruService.delete(created.getId());
        });

    }
    @Test@DisplayName("Testa GetById")
    public void testaGetByID(){
        Escala escala = escalaService.getById(322912L).get();

        GRU gru = new GRU();
        gru.setEscala(escala);
        gru.setValorReal(new BigDecimal("3000"));
        gru.setEmissao(LocalDate.now());
        GRU created = gruService.criarComPagTesouro(gru);
        assertNotNull(created);
        assertDoesNotThrow(() -> {
            GRU returned = gruService.getById(created.getId()).get();
            assertEquals(returned.getId(), created.getId());
        });
        //gruService.delete(created.getId());
    }
    @Test
    @DisplayName("Busca pela escala")
    public void testaGetByEscala(){
        Optional<GRU> byEscalaId = gruService.findByEscalaId(322913L);
        assertEquals(322913,byEscalaId.get().getId());
    }
    @Test
    @DisplayName("Testa Criteria")
    public void testaCriteria(){
        //GRUFilters gruFilters = new GRUFilters();

        //List<GRU> concluido = gruService.getWithFilters("CONCLUIDO");

        //assertEquals(3,concluido.size());
    }

}
