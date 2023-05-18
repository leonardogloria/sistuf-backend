package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.RazaoDeVisita;
import org.br.sistufbackend.repository.RazaoDeVisitaRepository;
import org.br.sistufbackend.service.RazaoDeVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RazaoDeVisitaServiceImpl implements RazaoDeVisitaService {
    @Autowired
    RazaoDeVisitaRepository razaoDeVisitaRepository;
    @Override
    public RazaoDeVisita create(RazaoDeVisita razaoDeVisita) {
        return razaoDeVisitaRepository.save(razaoDeVisita);
    }

    @Override
    public List<RazaoDeVisita> getAll() {
        return razaoDeVisitaRepository.findAll();
    }

    @Override
    public Optional<RazaoDeVisita> getById(Long id) {
        return razaoDeVisitaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        razaoDeVisitaRepository.deleteById(id);
    }
    @Override
    public void update(Long id, RazaoDeVisita razaoDeVisita) {
        RazaoDeVisita razaoLocalizado = razaoDeVisitaRepository.findById(id).get();
        razaoLocalizado.setCategoriaVisita(razaoDeVisita.getCategoriaVisita());
        razaoLocalizado.setRotulo(razaoDeVisita.getRotulo());
        razaoLocalizado.setRegraDeCobrancaTUF(razaoDeVisita.getRegraDeCobrancaTUF());
        razaoDeVisitaRepository.save(razaoLocalizado);
    }
}
