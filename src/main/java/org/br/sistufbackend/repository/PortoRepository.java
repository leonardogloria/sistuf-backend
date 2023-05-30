package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Porto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortoRepository extends JpaRepository<Porto,Long> {
    List<Porto> findAllByNomeContainsIgnoreCase(String nome);
}
