package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Pais;
import org.br.sistufbackend.repository.PaisRepository;
import org.br.sistufbackend.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisServiceImpl implements PaisService {
    @Autowired
    PaisRepository paisRepository;
    @Override
    public List<Pais> getAll(Integer pageSize) {
        Pageable page = Pageable.ofSize(pageSize);
        return paisRepository.findAll(page).get().toList();
    }

    @Override
    public Optional<Pais> getById(Long id) {
        return paisRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
            paisRepository.deleteById(id);
    }

    @Override
    public Pais save(Pais pais) {
        return paisRepository.save(pais);
    }

    @Override
    public List<Pais> findAllByNome(String nome) {
        return paisRepository.findAllByNomeStartsWithIgnoreCase(nome);
    }
    @Override
    public void update(Long id, Pais pais){
        Pais paisLocalizado = paisRepository.getReferenceById(id);
        paisLocalizado.setCodigoPaisAlpha2(pais.getCodigoPaisAlpha2());
        paisLocalizado.setNome(pais.getNome());
        paisLocalizado.setCodigoPaisAlpha3(pais.getCodigoPaisAlpha3());
        paisLocalizado.setCodigoDDI(pais.getCodigoDDI());
        paisLocalizado.setAcordoComBrasil(pais.isAcordoComBrasil());
        paisRepository.save(paisLocalizado);
    }

    @Override
    public Long count() {
        return paisRepository.count();
    }

    @Override
    public List<Pais> getAll(Integer size, Integer page) {
        PageRequest of = PageRequest.of(page, size);
        return paisRepository.findAll(of).stream().toList();
    }

    @Override
    public List<Pais> getAll() {
        Sort sort = Sort.by("nome").ascending();
        return paisRepository.findAll(sort).stream().toList();
    }
}
