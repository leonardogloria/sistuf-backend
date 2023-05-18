package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Estado;

import java.util.List;
import java.util.Optional;

public interface EstadoService {
    Estado save(Estado estado);
    Optional<Estado> getById(Long id);
    void deleteById(Long id);
    List<Estado> getAll();
    void update(Long id, Estado estado);

}
