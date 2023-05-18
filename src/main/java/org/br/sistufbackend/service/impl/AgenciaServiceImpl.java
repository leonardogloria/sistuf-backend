package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.repository.AgenciaRepository;
import org.br.sistufbackend.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenciaServiceImpl implements AgenciaService {
    @Autowired
    AgenciaRepository agenciaRepository;
    @Override
    public Agencia save(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

    @Override
    public Optional<Agencia> getById(Long id) {
        return agenciaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        agenciaRepository.deleteById(id);
    }

    @Override
    public List<Agencia> getAll() {
        return agenciaRepository.findAll();
    }

    @Override
    public void update(Long id, Agencia agencia) {
        Agencia retornada = agenciaRepository.findById(id).get();
        retornada.setBairro(agencia.getBairro());
        retornada.setCep(agencia.getCep());
        retornada.setCnpj(agencia.getCnpj());
        retornada.setCidade(agencia.getCidade());
        retornada.setContato(agencia.getContato());
        retornada.setEmail(agencia.getEmail());
        retornada.setEstado(agencia.getEstado());
        retornada.setNome(agencia.getNome());
        retornada.setComplemento(agencia.getComplemento());
        retornada.setNumero(agencia.getNumero());
        retornada.setLogradouro(agencia.getLogradouro());
        retornada.setTelefone(agencia.getTelefone());
        retornada.setRamal(agencia.getRamal());
        retornada.setStatus(agencia.getStatus());
        agenciaRepository.save(retornada);
    }
}
