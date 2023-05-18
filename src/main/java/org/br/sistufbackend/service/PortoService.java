package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Pais;
import org.br.sistufbackend.model.Porto;

import java.util.List;
import java.util.Optional;

public interface PortoService {
    List<Porto> getAll();
    Optional<Porto> getById(Long id);
    void deleteById(Long id);
    Porto save(Porto porto);

    void update(Long id, Porto porto);
}
