package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.repository.EstadoRepository;
import org.br.sistufbackend.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoServiceImpl implements EstadoService {
    @Autowired
    EstadoRepository estadoRepository;
    @Override
    public Estado save(Estado estado) {
        Estado saved = estadoRepository.save(estado);
        return saved;
    }

    @Override
    public Optional<Estado> getById(Long id) {
        return estadoRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        estadoRepository.deleteById(id);
    }

    @Override
    public List<Estado> getAll() {
        return estadoRepository.findAll();
    }

    @Override
    public void update(Long id, Estado estado) {
        Estado estadoExistente = estadoRepository.findById(id).get();
        estadoExistente.setUf(estado.getUf());
        estadoExistente.setNome(estado.getNome());
        estadoRepository.save(estadoExistente);
    }
}
