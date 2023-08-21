package org.br.sistufbackend.service;

import org.br.sistufbackend.model.GrupoUsuario;

import java.util.List;
import java.util.Optional;

public interface GrupoUsuarioService {
    List<GrupoUsuario> getAll();
    Optional<GrupoUsuario> getById(Long id);
    void deleteById(Long id);
    GrupoUsuario save(GrupoUsuario grupoUsuario);
    void update(Long id, GrupoUsuario grupoUsuario);
    Long count();

    List<GrupoUsuario> findByNome(String nome);

    List<GrupoUsuario> getAll(Integer page, Integer size);
}
