package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.RazaoDeVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazaoDeVisitaRepository extends JpaRepository<RazaoDeVisita, Long> {
}
