package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizacaoMilitarRepositoty extends JpaRepository<OrganizacaoMilitar, Long> {
    List<OrganizacaoMilitar> findAllByNomeContainsIgnoreCase(String nome);
}
