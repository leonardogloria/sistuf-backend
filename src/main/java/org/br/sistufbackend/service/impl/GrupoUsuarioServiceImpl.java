package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.exception.GrupoUsuarioValidationException;
import org.br.sistufbackend.model.GrupoUsuario;
import org.br.sistufbackend.repository.GrupoUsuarioRepository;
import org.br.sistufbackend.service.GrupoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoUsuarioServiceImpl implements GrupoUsuarioService {
    @Autowired
    GrupoUsuarioRepository grupoUsuarioRepository;
    @Override
    public List<GrupoUsuario> getAll() {
        return grupoUsuarioRepository.findAll(Sort.by("descricao")
                .ascending()).stream().toList();
    }

    @Override
    public Optional<GrupoUsuario> getById(Long id) {
        return grupoUsuarioRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        grupoUsuarioRepository.deleteById(id);
    }

    @Override
    public GrupoUsuario save(GrupoUsuario grupoUsuario) {
        if(grupoUsuarioRepository.existsByDescricao(grupoUsuario.getDescricao())){
            throw new GrupoUsuarioValidationException("Descrição já cadastrada");
        }
        return grupoUsuarioRepository.save(grupoUsuario);
    }


    @Override
    public void update(Long id, GrupoUsuario grupoUsuario) {
        GrupoUsuario retornado = grupoUsuarioRepository.findById(id).get();
        retornado.setDescricao(grupoUsuario.getDescricao());
        retornado.setIsVisivelAgencia(grupoUsuario.getIsVisivelAgencia());
        retornado.setIsVisivelCapitania(grupoUsuario.getIsVisivelCapitania());
        grupoUsuarioRepository.save(retornado);

    }

    @Override
    public Long count() {
        return grupoUsuarioRepository.count();
    }

    @Override
    public List<GrupoUsuario> findByNome(String nome) {
        return grupoUsuarioRepository.findAllByDescricaoIgnoreCase(nome);
    }

    @Override
    public List<GrupoUsuario> getAll(Integer page, Integer size) {
        PageRequest pagination = PageRequest.of(page, size, Sort.by("descricao").ascending());
        return grupoUsuarioRepository.findAll(pagination).stream().toList();
    }
}
