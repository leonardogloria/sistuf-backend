package org.br.sistufbackend.services;

import org.br.sistufbackend.model.*;
import org.br.sistufbackend.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "dev")
public class EscalaServiceTests {
    Logger logger = LoggerFactory.getLogger(EscalaServiceTests.class);
    @Autowired
    RoteiroService roteiroService;
    @Autowired
    NavioService navioService;
    @Autowired
    PortoService portoService;
    @Autowired
    EscalaService escalaService;
    @Autowired
    AgenciaService agenciaService;
    @Autowired
    CategoriaVisitaService categoriaVisitaService;
    @Autowired
    RazaoDeVisitaService razaoDeVisitaService;
    @Autowired
    GruService gruService;
    @Test
    @DisplayName("Deve testar regra de 2 primeiros e dois ultimos")
    public void testaRegra2Primeirose2Ultimos(){
        Navio navioDePassageiros = navioService.getById(16462L).get();//Navio de Passageiros
        Porto portoDeRecife = portoService.getById(11824L).get();
        Porto portoDeSantos = portoService.getById(12865L).get();

        LocalDateTime agora = LocalDateTime.now();
        Roteiro roteiro = Roteiro.builder().navio(navioDePassageiros)
                .entrada(agora.minusDays(20))
                .saida(agora.plusDays(10))
                .portoOrigem(portoDeRecife)
                .portoDestino(portoDeSantos).quantidadePortos(5).build();
        Roteiro roteiroCreated = roteiroService.save(roteiro);
        logger.info("--------Roteiro:-----:   " +  roteiroCreated.getId().toString());
        assertNotNull(roteiroCreated);

        Agencia agencia = agenciaService.getById(2237L).get();
        CategoriaVisita categoriaVisita = categoriaVisitaService.getById(1L).get();
        RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(13L).get();

        Escala escala1 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(10)).saida(agora.minusDays(9))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala2 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(8)).saida(agora.minusDays(7))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala3 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(6)).saida(agora.minusDays(5))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala4 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(4)).saida(agora.minusDays(3))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala5 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(2)).saida(agora.minusDays(1))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();

        Escala escalaCreated1 = escalaService.save(escala1);
        Escala escalaCreated2 = escalaService.save(escala2);
        Escala escalaCreated3 = escalaService.save(escala3);
        Escala escalaCreated4 = escalaService.save(escala4);
        Escala escalaCreated5 = escalaService.save(escala5);
        assertNotNull(escalaCreated1.getId());
        assertNotNull(escalaCreated2.getId());
        assertNotNull(escalaCreated3.getId());
        assertNotNull(escalaCreated4.getId());
        assertNotNull(escalaCreated5.getId());

        List<Long> escalasIds = List.of(escalaCreated1.getId(), escalaCreated2.getId(),
                escalaCreated3.getId(), escalaCreated4.getId(), escalaCreated5.getId());

        escalaService.criarGru(escalasIds);
        AtomicInteger grusCriadas = new AtomicInteger();
        escalasIds.forEach(escalaId -> {
            Optional<GRU> byEscalaId = gruService.findByEscalaId(escalaId);
            if(byEscalaId.isPresent()){
                grusCriadas.getAndIncrement();
                gruService.delete(escalaId);
            }
        });
        assertEquals(4,grusCriadas.get());

        escalaService.deleteById(escalaCreated5.getId());
        escalaService.deleteById(escalaCreated4.getId());
        escalaService.deleteById(escalaCreated3.getId());
        escalaService.deleteById(escalaCreated2.getId());
        escalaService.deleteById(escalaCreated1.getId());
        roteiroService.deleteById(roteiroCreated.getId());

    }
    @Test
    @DisplayName("Deve testar regra de Todos os portos Visitados")
    public void testaRegraTodosOsPortosNacionaisVisitados(){
        Navio navioDePassageiros = navioService.getById(35927L).get();//Navio de Pesquisa
        Porto portoDeRecife = portoService.getById(11824L).get();
        Porto portoDeSantos = portoService.getById(12865L).get();

        LocalDateTime agora = LocalDateTime.now();
        Roteiro roteiro = Roteiro.builder().navio(navioDePassageiros)
                .entrada(agora.minusDays(20))
                .saida(agora.plusDays(10))
                .portoOrigem(portoDeRecife)
                .portoDestino(portoDeSantos).quantidadePortos(5).build();
        Roteiro roteiroCreated = roteiroService.save(roteiro);
        logger.info("--------Roteiro:-----:   " +  roteiroCreated.getId().toString());
        assertNotNull(roteiroCreated);

        Agencia agencia = agenciaService.getById(2237L).get();
        CategoriaVisita categoriaVisita = categoriaVisitaService.getById(1L).get();
        RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(13L).get();

        Escala escala1 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(10)).saida(agora.minusDays(9))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala2 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(8)).saida(agora.minusDays(7))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala3 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(6)).saida(agora.minusDays(5))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala4 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(4)).saida(agora.minusDays(3))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala5 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(2)).saida(agora.minusDays(1))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();

        Escala escalaCreated1 = escalaService.save(escala1);
        Escala escalaCreated2 = escalaService.save(escala2);
        Escala escalaCreated3 = escalaService.save(escala3);
        Escala escalaCreated4 = escalaService.save(escala4);
        Escala escalaCreated5 = escalaService.save(escala5);
        assertNotNull(escalaCreated1.getId());
        assertNotNull(escalaCreated2.getId());
        assertNotNull(escalaCreated3.getId());
        assertNotNull(escalaCreated4.getId());
        assertNotNull(escalaCreated5.getId());

        List<Long> escalasIds = List.of(escalaCreated1.getId(), escalaCreated2.getId(),
                escalaCreated3.getId(), escalaCreated4.getId(), escalaCreated5.getId());

        escalaService.criarGru(escalasIds);
        AtomicInteger grusCriadas = new AtomicInteger();
        escalasIds.forEach(escalaId -> {
            Optional<GRU> byEscalaId = gruService.findByEscalaId(escalaId);
            if(byEscalaId.isPresent()){
                grusCriadas.getAndIncrement();
                gruService.delete(escalaId);
            }
        });
        assertEquals(5,grusCriadas.get());

        escalaService.deleteById(escalaCreated5.getId());
        escalaService.deleteById(escalaCreated4.getId());
        escalaService.deleteById(escalaCreated3.getId());
        escalaService.deleteById(escalaCreated2.getId());
        escalaService.deleteById(escalaCreated1.getId());
        roteiroService.deleteById(roteiroCreated.getId());

    }

    @Test
    @DisplayName("Deve popular os Dados")
    public void populaDados(){
        Navio navioDePassageiros = navioService.getById(35927L).get();//Navio de Pesquisa
        Porto portoDeRecife = portoService.getById(11824L).get();
        Porto portoDeSantos = portoService.getById(12865L).get();

        LocalDateTime agora = LocalDateTime.now();
        Roteiro roteiro = Roteiro.builder().navio(navioDePassageiros)
                .entrada(agora.minusDays(20))
                .saida(agora.plusDays(10))
                .portoOrigem(portoDeRecife)
                .portoDestino(portoDeSantos).quantidadePortos(5).build();
        Roteiro roteiroCreated = roteiroService.save(roteiro);
        logger.info("--------Roteiro:-----:   " +  roteiroCreated.getId().toString());
        assertNotNull(roteiroCreated);

        Agencia agencia = agenciaService.getById(2237L).get();
        CategoriaVisita categoriaVisita = categoriaVisitaService.getById(1L).get();
        RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(13L).get();

        Escala escala1 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(10)).saida(agora.minusDays(9))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala2 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(8)).saida(agora.minusDays(7))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala3 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(6)).saida(agora.minusDays(5))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala4 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(4)).saida(agora.minusDays(3))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();
        Escala escala5 = Escala.builder().agencia(agencia)
                .roteiro(roteiroCreated).chegada(agora.minusDays(2)).saida(agora.minusDays(1))
                .categoriaVisita(categoriaVisita).razaoDeVisita(razaoDeVisita)
                .porto(portoDeSantos).build();

        Escala escalaCreated1 = escalaService.save(escala1);
        Escala escalaCreated2 = escalaService.save(escala2);
        Escala escalaCreated3 = escalaService.save(escala3);
        Escala escalaCreated4 = escalaService.save(escala4);
        Escala escalaCreated5 = escalaService.save(escala5);
        assertNotNull(escalaCreated1.getId());
        assertNotNull(escalaCreated2.getId());
        assertNotNull(escalaCreated3.getId());
        assertNotNull(escalaCreated4.getId());
        assertNotNull(escalaCreated5.getId());

        List<Long> escalasIds = List.of(escalaCreated1.getId(), escalaCreated2.getId(),
                escalaCreated3.getId(), escalaCreated4.getId(), escalaCreated5.getId());



        //escalaService.deleteById(escalaCreated5.getId());
        //escalaService.deleteById(escalaCreated4.getId());
        //escalaService.deleteById(escalaCreated3.getId());
        //escalaService.deleteById(escalaCreated2.getId());
        //escalaService.deleteById(escalaCreated1.getId());
        //roteiroService.deleteById(roteiroCreated.getId());

    }




}
