package org.br.sistufbackend.service.impl;


import org.br.sistufbackend.model.CategoriaVisita;
import org.br.sistufbackend.repository.CategoriaVisitaRepository;
import org.br.sistufbackend.service.CategoriaVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaVisitaServiceImpl implements CategoriaVisitaService {
    @Autowired
    CategoriaVisitaRepository categoriaVisitaRepository;
    @Override
    public CategoriaVisita save(CategoriaVisita categoriaVisita) {
        return categoriaVisitaRepository.save(categoriaVisita);
    }

    @Override
    public Optional<CategoriaVisita> getById(Long id) {
        return categoriaVisitaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        categoriaVisitaRepository.deleteById(id);
    }

    @Override
    public List<CategoriaVisita> getAll(Integer pagesize, Integer page) {
        PageRequest of = PageRequest.of(page, pagesize);
        return categoriaVisitaRepository.findAll(of).stream().toList();
    }

    @Override
    public void update(Long id, CategoriaVisita categoriaVisita) {
        CategoriaVisita categoriaVisitaRetornada = categoriaVisitaRepository.findById(id).get();
        categoriaVisitaRetornada.setDescricaoLei(categoriaVisita.getDescricaoLei());
        categoriaVisitaRetornada.setDescricaoDetalhada(categoriaVisita.getDescricaoDetalhada());
        categoriaVisitaRetornada.setDescricaoAbreviada(categoriaVisita.getDescricaoAbreviada());
        categoriaVisitaRetornada.setInicioVigencia(categoriaVisita.getInicioVigencia());
        categoriaVisitaRetornada.setTerminoVigencia(categoriaVisita.getTerminoVigencia());
        categoriaVisitaRepository.save(categoriaVisitaRetornada);

    }

    @Override
    public List<CategoriaVisita> findByDescricaoDetalhada(String descricao, Integer size) {
        Pageable pageable = Pageable.ofSize(size);
        return categoriaVisitaRepository.findAllByDescricaoDetalhadaContainsIgnoreCase(descricao,pageable);
    }

    @Override
    public Long count() {
        return categoriaVisitaRepository.count();
    }

    @Override
    public List<CategoriaVisita> getAll() {
        return categoriaVisitaRepository.findAll();
    }

}
