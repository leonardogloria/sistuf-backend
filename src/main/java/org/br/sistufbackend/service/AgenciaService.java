package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface AgenciaService {
    Agencia save(Agencia agencia);
    Optional<Agencia> getById(Long id);
    void deleteById(Long id);
    List<Agencia> getAll();
    void update(Long id,Agencia agencia);
    Long count();
    List<Agencia> findByNome(String nome);

    List<Agencia> getAll(Integer size, Integer page);
    List<Usuario> findAllByAgenciaId(Long id);
    Long findAgenciaDoUsuarioById(String id);
    void associaUsuarioAAgencia(String login, Long agenciaId);

    boolean isUsuarioInAgencia(String id);
}
