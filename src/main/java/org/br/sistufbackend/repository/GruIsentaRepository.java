package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.GRU;
import org.br.sistufbackend.model.GRUIsenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GruIsentaRepository extends JpaRepository<GRUIsenta,Long> {
    Optional<GRU> findByEscalaId(Long id);
}
