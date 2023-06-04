package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Roteiro;

import java.util.List;
import java.util.Optional;

public interface RoteiroService {
    Roteiro save(Roteiro roteiro);
    Optional<Roteiro> getById(Long id);
    void deleteById(Long id);
    List<Roteiro> getAll();
    void update(Long id, Roteiro roteiro);
    Long count();
    List<Roteiro> getAll(Integer page, Integer size);

}
