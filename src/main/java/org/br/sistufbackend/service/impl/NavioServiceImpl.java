package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.repository.NavioRepository;
import org.br.sistufbackend.service.NavioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavioServiceImpl implements NavioService {
    @Autowired
    NavioRepository navioRepository;
    @Override
    public Navio save(Navio navio) {
        return navioRepository.save(navio);
    }

    @Override
    public Optional<Navio> getById(Long id) {
        return navioRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        navioRepository.deleteById(id);
    }

    @Override
    public List<Navio> getAll() {
        return navioRepository.findAll();
    }

    @Override
    public void update(Long id, Navio navio) {
        Navio retornado = navioRepository.findById(id).get();
        retornado.setPais(navio.getPais());
        retornado.setTipoNavio(navio.getTipoNavio());
        retornado.setNumeroNavioIMO(navio.getNumeroNavioIMO());
        retornado.setIrin(navio.getIrin());
        retornado.setArmador(navio.getArmador());
        retornado.setNome(navio.getNome());
        retornado.setTonelagemPesoBruto(navio.getTonelagemPesoBruto());
        retornado.setVelocidadeCruzeiro(navio.getVelocidadeCruzeiro());
        retornado.setDocumentacao(navio.getDocumentacao());
        retornado.setTelefone(navio.getTelefone());
        retornado.setPagaIndependenteReciprocidade(navio.getPagaIndependenteReciprocidade());
        navioRepository.save(retornado);
    }
}
