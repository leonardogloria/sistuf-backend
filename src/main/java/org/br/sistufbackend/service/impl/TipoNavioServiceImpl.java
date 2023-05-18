package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.TipoNavio;
import org.br.sistufbackend.repository.TipoNavioRepository;
import org.br.sistufbackend.service.TipoNavioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoNavioServiceImpl implements TipoNavioService {
    @Autowired
    TipoNavioRepository navioRepository;
    @Override
    public TipoNavio save(TipoNavio navio) {
        return navioRepository.save(navio);
    }

    @Override
    public Optional<TipoNavio> getById(Long id) {
        return navioRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        navioRepository.deleteById(id);
    }

    @Override
    public List<TipoNavio> getAll() {
        return navioRepository.findAll();
    }

    @Override
    public void update(Long id, TipoNavio navio) {
        TipoNavio localizado = navioRepository.findById(id).get();
        localizado.setTipoDeNavio(navio.getTipoDeNavio());
        localizado.setCategoriaVisita(navio.getCategoriaVisita());
        localizado.setRegraDeCobrancaTUF(navio.getRegraDeCobrancaTUF());
        navioRepository.save(localizado);
    }
}
