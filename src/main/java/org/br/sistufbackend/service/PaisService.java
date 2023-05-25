package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Pais;

import java.util.List;
import java.util.Optional;

public interface PaisService {
    List<Pais> getAll(Integer pageSize);
    Optional<Pais> getById(Long id);
    void deleteById(Long id);
    Pais save(Pais pais);
    List<Pais> findAllByNome(String nome);
    void update(Long id, Pais pais);
    Long count();

    List<Pais> getAll(Integer size, Integer page);
}
