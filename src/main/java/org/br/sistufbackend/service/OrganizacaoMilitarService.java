package org.br.sistufbackend.service;

import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface OrganizacaoMilitarService {
    List<OrganizacaoMilitar> getAll();
    Optional<OrganizacaoMilitar> getById(Long id);
    void deleteById(Long id);
    OrganizacaoMilitar save(OrganizacaoMilitar organizacaoMilitar);
    void update(Long id, OrganizacaoMilitar organizacaoMilitar);
    Long count();

    List<OrganizacaoMilitar> findByNome(String nome);

    List<OrganizacaoMilitar> getAll(Integer size, Integer page);
    Long findOrganizacaoDoUsuarioById(String login);
    List<Usuario> findAllUsuariosByOrgMilId(Long id);
    void associarUsuarioAOrganizacao(String login, Long organizacao);


     boolean isUsuarioInOrgMil(String login);
}
