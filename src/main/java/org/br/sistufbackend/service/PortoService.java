package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.model.dto.PortoDTO;

import java.util.List;
import java.util.Optional;

public interface PortoService {
    List<PortoDTO> getAll();
    Optional<Porto> getById(Long id);
    void deleteById(Long id);
    Porto save(Porto porto);
    void update(Long id, Porto porto);
    Long count();
    List<Porto> findByName(String nome);
    List<Porto> getAll(Integer page, Integer size);
}
