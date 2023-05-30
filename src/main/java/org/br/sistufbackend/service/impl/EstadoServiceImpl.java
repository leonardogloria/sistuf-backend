package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.repository.EstadoRepository;
import org.br.sistufbackend.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Optional<Estado> getById(String id) {
        return estadoRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        estadoRepository.deleteById(id);
    }

    @Override
    public List<Estado> findAllByName(String name) {
        return estadoRepository.findAllByNomeContainsIgnoreCase(name);
    }

    @Override
    public List<Estado> getAll() {
        Sort byNome = Sort.by("nome").ascending();
        return estadoRepository.findAll(byNome);
    }

    @Override
    public List<Estado> getAll(Integer pageSize) {
        Pageable page = Pageable.ofSize(pageSize);
        return estadoRepository.findAll(page).get().toList();

    }

    @Override
    public void update(String id, Estado estado) {
        Estado estadoExistente = estadoRepository.findById(id).get();
        estadoExistente.setUf(estado.getUf());
        estadoExistente.setNome(estado.getNome());
        estadoRepository.save(estadoExistente);
    }

    @Override
    public List<Estado> getAll(Integer size, Integer page) {
        PageRequest of = PageRequest.of(page, size);
        return estadoRepository.findAll(of).get().toList();
    }

    @Override
    public Long count() {
        return estadoRepository.count();
    }
}
