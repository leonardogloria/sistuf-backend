package org.br.sistufbackend.services;

import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.service.EstadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles(value = "test")
public class EstadoServiceTest {
    @Autowired
    EstadoService estadoService;
    @Test
    void testaGetAll(){
        List<Estado> all = this.estadoService.getAll(10, 0);
        assertEquals(10, all.size());
    }
    @Test
    void testaInsert(){
        Long before = estadoService.count();
        Estado build = Estado.builder().uf("TE").nome("TESTE").build();
        estadoService.save(build);
        Long after = estadoService.count();
        assertEquals(before + 1, after);
        estadoService.deleteById("TE");

    }
    @Test
    void testaRemove(){
        Long before = estadoService.count();
        Estado build = Estado.builder().uf("TE").nome("TESTE").build();
        estadoService.save(build);
        estadoService.deleteById("TE");
        Long after = estadoService.count();
        assertEquals(before , after);
    }
    @Test
    void testaUpdate(){
        Estado rj = estadoService.getById("RJ").get();
        rj.setNome("RIO DE JANEIRO 2");
        estadoService.save(rj);
        rj = estadoService.getById("RJ").get();
        assertEquals("RIO DE JANEIRO 2", rj.getNome());

        rj = estadoService.getById("RJ").get();
        rj.setNome("RIO DE JANEIRO");
        estadoService.save(rj);
    }

}
