package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.repository.OrganizacaoMilitarRepositoty;
import org.br.sistufbackend.service.OrganizacaoMilitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizacaoMilitarServiceImpl implements OrganizacaoMilitarService {
    @Autowired
    OrganizacaoMilitarRepositoty omRepositoty;
    @Override
    public List<OrganizacaoMilitar> getAll() {
        return omRepositoty.findAll();
    }

    @Override
    public Optional<OrganizacaoMilitar> getById(Long id) {
        return omRepositoty.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        omRepositoty.deleteById(id);
    }

    @Override
    public OrganizacaoMilitar save(OrganizacaoMilitar organizacaoMilitar) {
        return omRepositoty.save(organizacaoMilitar);
    }

    @Override
    public void update(Long id, OrganizacaoMilitar om) {
        OrganizacaoMilitar retornada = omRepositoty.findById(id).get();
        retornada.setBairro(om.getBairro());
        retornada.setCep(om.getCep());
        retornada.setCidade(om.getCidade());
        retornada.setEmail(om.getEmail());
        retornada.setEstado(om.getEstado());
        retornada.setComplemento(om.getComplemento());
        retornada.setNome(om.getNome());
        retornada.setNumero(om.getNumero());
        retornada.setSigla(om.getSigla());
        retornada.setSistelma(om.getSistelma());
        retornada.setEstado(om.getEstado());
        retornada.setIndicativo(om.getIndicativo());
        retornada.setLogradouro(om.getLogradouro());
        retornada.setCnpj(om.getCnpj());
        retornada.setCodigo(om.getCodigo());
        omRepositoty.save(retornada);


    }
}
