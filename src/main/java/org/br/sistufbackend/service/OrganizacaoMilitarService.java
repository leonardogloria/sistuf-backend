package org.br.sistufbackend.service;

import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.model.Pais;

import java.util.List;
import java.util.Optional;

public interface OrganizacaoMilitarService {
    List<OrganizacaoMilitar> getAll();
    Optional<OrganizacaoMilitar> getById(Long id);
    void deleteById(Long id);
    OrganizacaoMilitar save(OrganizacaoMilitar organizacaoMilitar);
    void update(Long id, OrganizacaoMilitar organizacaoMilitar);

}
