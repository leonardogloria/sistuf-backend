package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.CategoriaVisita;

import java.util.List;
import java.util.Optional;

public interface AgenciaService {
    Agencia save(Agencia agencia);
    Optional<Agencia> getById(Long id);
    void deleteById(Long id);
    List<Agencia> getAll();
    void update(Long id,Agencia agencia);
}
