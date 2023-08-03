package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.model.Roteiro;

import java.util.List;
import java.util.Optional;

public interface EscalaService {
    Escala save(Escala escala);
    Optional<Escala> getById(Long id);
    void deleteById(Long id);
    List<Escala> getAll();
    void update(Long id, Escala escala);
    Long count();
    List<Escala> getAll(Integer page, Integer size);
    List<Escala> getAllByRoteiroId(Long id);

}
