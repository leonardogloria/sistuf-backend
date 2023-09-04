package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.repository.EscalaRepository;
import org.br.sistufbackend.service.EscalaService;
import org.br.sistufbackend.service.ValidarEscalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EscalaServiceImpl implements EscalaService {
    @Autowired
    EscalaRepository escalaRepository;
    @Autowired
    ValidarEscalaService validarEscalaService;


    @Override
    public Escala save(Escala escala) {
        validarEscalaService.validarDataSaida(escala);
        validarEscalaService.validarQuantidadeDePortos(escala, getAllByRoteiroId(escala.getRoteiro().getId()));
        validarEscalaService.validarRangeDeDatas(escala);
        return escalaRepository.save(escala);
    }

    @Override
    public Optional<Escala> getById(Long id) {
        return escalaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Escala escala = getById(id).get();
        validarEscalaService.validarSeEhUltimaEscala(escala, getAllByRoteiroId(escala.getRoteiro().getId()));
        escalaRepository.deleteById(id);
    }
    @Override
    public List<Escala> getAll() {
        return escalaRepository.findAll();
    }

    @Override
    public void update(Long id, Escala escala) {
        Escala escalaExistente = escalaRepository.findById(id).get();

        escalaExistente.setAgencia(escala.getAgencia());
        escalaExistente.setPorto(escala.getPorto());
        escalaExistente.setCategoriaVisita(escala.getCategoriaVisita());
        escalaExistente.setRazaoDeVisita(escala.getRazaoDeVisita());
        escalaExistente.setChegada(escala.getChegada());
        escalaExistente.setSaida(escala.getSaida());
        validarEscalaService.validarDataSaida(escalaExistente);
        validarEscalaService.validarRangeDeDatas(escalaExistente);
        validarEscalaService.validarSeEhUltimaEscala(escalaExistente, getAllByRoteiroId(escalaExistente.getRoteiro().getId()));

        escalaRepository.save(escalaExistente);


    }

    @Override
    public Long count() {
        return escalaRepository.count();
    }

    @Override
    public List<Escala> getAll(Integer page, Integer size) {
        PageRequest of = PageRequest.of(page, size);
        return escalaRepository.findAll(of).stream().toList();
    }

    @Override
    public List<Escala> getAllByRoteiroId(Long id) {
        Sort order = Sort.by(Sort.Direction.DESC, "saida");
        return escalaRepository.findAllByRoteiroId(id,order);
    }
}
