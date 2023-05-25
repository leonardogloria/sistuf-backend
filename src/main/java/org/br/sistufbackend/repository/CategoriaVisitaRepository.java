package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.CategoriaVisita;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaVisitaRepository extends JpaRepository<CategoriaVisita,Long>, PagingAndSortingRepository<CategoriaVisita,Long> {
    List<CategoriaVisita> findAllByDescricaoDetalhadaContainsIgnoreCase(String criteria, Pageable size);
}
