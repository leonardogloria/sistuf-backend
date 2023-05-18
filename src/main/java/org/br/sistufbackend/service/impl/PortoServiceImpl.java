package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.repository.PortoRepository;
import org.br.sistufbackend.service.PortoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortoServiceImpl implements PortoService {
    @Autowired
    PortoRepository portoRepository;
    @Override
    public List<Porto> getAll() {
        return portoRepository.findAll();
    }

    @Override
    public Optional<Porto> getById(Long id) {
        return portoRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        portoRepository.deleteById(id);
    }

    @Override
    public Porto save(Porto porto) {
        return portoRepository.save(porto);
    }

    @Override
    public void update(Long id, Porto porto) {
        Porto retornado = portoRepository.findById(id).get();
        retornado.setNome(porto.getNome());
        retornado.setOrganizacaoMilitar(porto.getOrganizacaoMilitar());
        retornado.setPais(porto.getPais());
        retornado.setCodigoONU(porto.getCodigoONU());
        retornado.setCodigoDPC(porto.getCodigoDPC());
        retornado.setCep(porto.getCep());
        retornado.setLogradouro(porto.getLogradouro());
        retornado.setNumero(porto.getNumero());
        retornado.setComplemento(porto.getComplemento());
        retornado.setBairro(porto.getBairro());
        retornado.setCidade(porto.getCidade());
        retornado.setEstado(porto.getEstado());
        portoRepository.save(retornado);
    }
}
