package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Estado;

import java.util.List;
import java.util.Optional;

public interface EstadoService {
    Estado save(Estado estado);
    Optional<Estado> getById(String id);
    void deleteById(String id);
    List<Estado> findAllByName(String name);

    List<Estado> getAll();
    void update(String id, Estado estado);

}
