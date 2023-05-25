package org.br.sistufbackend.service;

import org.br.sistufbackend.model.RazaoDeVisita;

import java.util.List;
import java.util.Optional;

public interface RazaoDeVisitaService {
    RazaoDeVisita create(RazaoDeVisita razaoDeVisita);
    List<RazaoDeVisita> getAll();
    Optional<RazaoDeVisita> getById(Long id);
    void deleteById(Long id);
    void update(Long id, RazaoDeVisita razaoDeVisita);
    long count();
    List<RazaoDeVisita> getAll(int pageSize, int page);
    List<RazaoDeVisita> findAllByRotulo(String rotulo);

}
