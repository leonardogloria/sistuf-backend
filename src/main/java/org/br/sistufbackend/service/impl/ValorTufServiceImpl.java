package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.ValorTUF;
import org.br.sistufbackend.repository.ValorTufRepository;
import org.br.sistufbackend.service.ValorTufService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValorTufServiceImpl implements ValorTufService {
    @Autowired
    ValorTufRepository valorTufRepository;
    @Override
    public List<ValorTUF> getAll() {
        return valorTufRepository.findAll();
    }
    public Long count(){
        return valorTufRepository.count();
    }

    @Override
    public ValorTUF create(ValorTUF valorTUF) {
        return valorTufRepository.save(valorTUF);
    }
}
