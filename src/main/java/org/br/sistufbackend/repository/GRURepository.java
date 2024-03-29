package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.GRU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GRURepository extends JpaRepository<GRU, Long> {
    Optional<GRU> findByEscalaId(Long id);

}
