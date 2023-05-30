package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgenciaRepository extends JpaRepository<Agencia,Long> {
    List<Agencia> findAllByNomeContainsIgnoreCase(String nome);
}
