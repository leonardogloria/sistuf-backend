package org.br.sistufbackend.service;

import org.br.sistufbackend.model.CategoriaVisita;

import java.util.List;
import java.util.Optional;

public interface CategoriaVisitaService {
    CategoriaVisita save(CategoriaVisita categoriaVisita);
    Optional<CategoriaVisita> getById(Long id);
    void deleteById(Long id);
    List<CategoriaVisita> getAll();
    void update(Long id,CategoriaVisita categoriaVisita);

}
