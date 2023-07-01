package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.exception.NavioValidationException;
import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.dto.NavioDTO;
import org.br.sistufbackend.repository.NavioRepository;
import org.br.sistufbackend.service.NavioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NavioServiceImpl implements NavioService {
    @Autowired
    NavioRepository navioRepository;
    @Override
    public Navio save(Navio navio) {
        existisByImo(navio.getNumeroNavioIMO());
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
    public List<NavioDTO> getAll() {
        return navioRepository.findAllLazy();
    }

    @Override
    public void update(Long id, Navio navio) {
        existisByImo(navio.getNumeroNavioIMO());
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

    @Override
    public Long count() {
        return navioRepository.count();
    }

    @Override
    public List<Navio> getAll(Integer page, Integer size) {
        PageRequest pagindAndSorting = PageRequest.of(page,size, Sort.by("id").ascending());
        return navioRepository.findAll(pagindAndSorting).stream().toList();
    }

    @Override
    public List<Navio> findByName(String nome) {
        return navioRepository.findAllByNomeContainsIgnoreCase(nome);
    }

    @Override
    public void existisByImo(String numero) {
        if(navioRepository.existsNavioByNumeroNavioIMO(numero)){
            throw new NavioValidationException("Número IMO Já existe");
        }
    }
}
