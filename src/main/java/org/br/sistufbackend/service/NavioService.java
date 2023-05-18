package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.model.Navio;

import java.util.List;
import java.util.Optional;

public interface NavioService {
    Navio save(Navio navio);
    Optional<Navio> getById(Long id);
    void deleteById(Long id);
    List<Navio> getAll();
    void update(Long id, Navio navio);
}
