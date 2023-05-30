package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.CotacaoDolar;
import org.br.sistufbackend.model.Estado;
import org.br.sistufbackend.repository.CotacaoDolarRepository;
import org.br.sistufbackend.service.CotacaoDolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class CotacaoDolarServiceImpl implements CotacaoDolarService {

    @Autowired
    CotacaoDolarRepository cotacaoDolarRepository;
    @Override
    public CotacaoDolar save(CotacaoDolar cotacaoDolar) {
        return cotacaoDolarRepository.save(cotacaoDolar);
    }

    @Override
    public Optional<CotacaoDolar> getById(LocalDate id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(LocalDate id) {
        cotacaoDolarRepository.deleteById(id);
    }

    @Override
    public List<CotacaoDolar> findAllByName(String name) {
        return null;
    }
    @Override
    public List<CotacaoDolar> getAll() {
        return null;
    }
    @Override
    public List<CotacaoDolar> getAll(Integer pageSize) {
        return null;
    }

    @Override
    public void update(String id, CotacaoDolar estado) {
    }
    @Override
    public List<CotacaoDolar> getAll(Integer size, Integer page) {
        PageRequest of = PageRequest.of(page,size, Sort.by("data").descending());
        return cotacaoDolarRepository.findAll(of).stream().toList();

    }

    @Override
    public Long count() {
        return cotacaoDolarRepository.count();
    }
}
