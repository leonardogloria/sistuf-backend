package org.br.sistufbackend.service;

import org.br.sistufbackend.model.GRU;
import org.br.sistufbackend.model.GRUIsenta;
import org.br.sistufbackend.model.payload.GRUFilters;

import java.util.List;
import java.util.Optional;

public interface GruService {
    GRU criarComPagTesouro(GRU gru);
    void delete(long id);
    GRU save(GRU gru);

    Optional<GRU> getById(Long id);
    Optional<GRU> findByEscalaId(Long id);
    void update(GRU gru);
    List<GRU> getWithFilters(GRUFilters filters);
    List<GRUIsenta> getIsentaWithFilters(GRUFilters filters);

    void isentarGru(Long id);
}
