package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.dto.NavioDTO;

import java.util.List;
import java.util.Optional;

public interface NavioService {
    Navio save(Navio navio);
    Optional<Navio> getById(Long id);
    void deleteById(Long id);
    List<NavioDTO> getAll();
    void update(Long id, Navio navio);
    Long count();

    List<Navio> getAll(Integer page, Integer size);

    List<Navio> findByName(String nome);
}
