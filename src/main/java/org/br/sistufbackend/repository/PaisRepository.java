package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais,Long> {
    List<Pais> findAllByNomeStartsWithIgnoreCase(String nome);

}
