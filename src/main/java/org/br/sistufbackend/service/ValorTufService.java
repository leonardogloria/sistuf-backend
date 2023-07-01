package org.br.sistufbackend.service;

import org.br.sistufbackend.model.ValorTUF;

import java.util.List;

public interface ValorTufService {
    List<ValorTUF> getAll();
    Long count();
    ValorTUF create(ValorTUF valorTUF);
    void deleteById(Long id);

}
