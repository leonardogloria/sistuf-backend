package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.CategoriaVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaVisitaRepository extends JpaRepository<CategoriaVisita,Long> {
}
