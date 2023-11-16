package org.br.sistufbackend.service;

import org.br.sistufbackend.model.CotacaoDolar;
import org.br.sistufbackend.model.Estado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CotacaoDolarService {
    CotacaoDolar save(CotacaoDolar cotacaoDolar);
    Optional<CotacaoDolar> getById(LocalDate id);
    void deleteById(LocalDate id);
    List<CotacaoDolar> findAllByName(String name);

    List<CotacaoDolar> getAll();
    List<CotacaoDolar> getAll(Integer pageSize);
    void update(String id, CotacaoDolar estado);

    List<CotacaoDolar> getAll(Integer size, Integer page);
    Long count();
    CotacaoDolar getLast();
}
