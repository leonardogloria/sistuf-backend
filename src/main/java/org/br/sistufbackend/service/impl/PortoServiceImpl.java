package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.model.dto.PortoDTO;
import org.br.sistufbackend.repository.PortoRepository;
import org.br.sistufbackend.service.PortoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortoServiceImpl implements PortoService {
    @Autowired
    PortoRepository portoRepository;
    @Override
    public List<PortoDTO> getAll() {
        return portoRepository.findAllLazy();
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
        retornado.setCodigoUNE(porto.getCodigoUNE());
        retornado.setCodigoDPC(porto.getCodigoDPC());
        retornado.setCep(porto.getCep());
        retornado.setLogradouro(porto.getLogradouro());
        retornado.setNumero(porto.getNumero());
        retornado.setComplemento(porto.getComplemento());
        retornado.setBairro(porto.getBairro());
        retornado.setCidade(porto.getCidade());
        retornado.setEstado(porto.getEstado());
        retornado.setCoordenadasGps(porto.getCoordenadasGps());
        portoRepository.save(retornado);
    }

    @Override
    public Long count() {
        return portoRepository.count();
    }

    @Override
    public List<Porto> findByName(String nome) {
        return portoRepository.findAllByNomeContainsIgnoreCase(nome);
    }
    @Override
    public List<Porto> getAll(Integer page, Integer size) {
        PageRequest of = PageRequest.of(page,size, Sort.by("id").ascending());
        return portoRepository.findAll(of).stream().toList();
    }
}
