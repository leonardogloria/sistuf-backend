package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.TipoNavio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoNavioRepository extends JpaRepository<TipoNavio,Long> {
    List<TipoNavio> findAllByTipoDeNavioContainsIgnoreCase(String nome);
}
