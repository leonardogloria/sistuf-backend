package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.repository.RoteiroRepository;
import org.br.sistufbackend.service.RoteiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoteiroServiceImpl implements RoteiroService {
    @Autowired
    RoteiroRepository roteiroRepository;
    @Override
    public Roteiro save(Roteiro roteiro) {
        return roteiroRepository.save(roteiro);
    }

    @Override
    public Optional<Roteiro> getById(Long id) {
        return roteiroRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        roteiroRepository.deleteById(id);
    }

    @Override
    public List<Roteiro> getAll() {
        return roteiroRepository.findAll();
    }

    @Override
    public void update(Long id, Roteiro roteiro) {
        Roteiro retornado = roteiroRepository.findById(id).get();
        retornado.setEntrada(roteiro.getEntrada());
        retornado.setNavio(roteiro.getNavio());
        retornado.setSaida(roteiro.getSaida());
        retornado.setPortoOrigem(roteiro.getPortoOrigem());
        retornado.setPortoDestino(roteiro.getPortoDestino());
        retornado.setQuantidadePortos(roteiro.getQuantidadePortos());
        roteiroRepository.save(retornado);
    }

    @Override
    public Long count() {
        return roteiroRepository.count();
    }

    @Override
    public List<Roteiro> getAll(Integer page, Integer size) {
        PageRequest pagindAndSorting = PageRequest.of(page,size, Sort.by("entrada").descending());
        return roteiroRepository.findAll(pagindAndSorting).stream().toList();
    }
}
