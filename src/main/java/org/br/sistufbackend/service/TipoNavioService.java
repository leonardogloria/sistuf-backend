package org.br.sistufbackend.service;

import org.br.sistufbackend.model.TipoNavio;

import java.util.List;
import java.util.Optional;

public interface TipoNavioService {
    TipoNavio save(TipoNavio navio);
    Optional<TipoNavio> getById(Long id);
    void deleteById(Long id);
    List<TipoNavio> getAll();
    void update(Long id, TipoNavio navio);
    Long count();
    List<TipoNavio> getAll(int pageSize, int page);

    List<TipoNavio> findAllByNome(String nome);
}
