package org.br.sistufbackend.service;

import org.br.sistufbackend.model.CategoriaVisita;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoriaVisitaService {
    CategoriaVisita save(CategoriaVisita categoriaVisita);
    Optional<CategoriaVisita> getById(Long id);
    void deleteById(Long id);
    List<CategoriaVisita> getAll(Integer pagesize, Integer page);
    void update(Long id,CategoriaVisita categoriaVisita);
    List<CategoriaVisita> findByDescricaoDetalhada(String descricao, Integer size);
    Long count();
}
