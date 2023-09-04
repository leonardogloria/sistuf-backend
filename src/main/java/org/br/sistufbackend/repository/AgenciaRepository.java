package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.security.SecUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgenciaRepository extends JpaRepository<Agencia,Long> {
    List<Agencia> findAllByNomeContainsIgnoreCase(String nome);

}
