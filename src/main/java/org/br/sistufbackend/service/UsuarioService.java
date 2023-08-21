package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    Optional<Usuario> getById(String id);
    void deleteById(String id);
    List<Usuario> getAll();
    void update(String id, Usuario usuario);
    Long count();
    List<Usuario> getAll(Integer page, Integer size);
}
