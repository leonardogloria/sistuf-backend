package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.exception.UsuarioValidationException;
import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.repository.UsuarioRepository;
import org.br.sistufbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    public Usuario save(Usuario usuario) {
        if(usuarioRepository.existsById(usuario.getId())) throw new UsuarioValidationException("Login já Cadastrado");
        if(usuarioRepository.existsByCpf(usuario.getCpf())) throw new UsuarioValidationException("CPF já Cadastrado");
        if(usuarioRepository.existsByNip(usuario.getNip()))  throw new UsuarioValidationException("NIP já cadastrado");
        if(usuarioRepository.existsByEmail(usuario.getEmail())) throw new UsuarioValidationException("E-Mail já cadastrado");
        if(usuarioRepository.existsById(usuario.getId()))  throw new UsuarioValidationException("Login já cadastrado");

        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> getById(String id) {
        return this.usuarioRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void update(String id, Usuario usuario) {
        Usuario retornado = usuarioRepository.findById(id).get();
        retornado.setSenha(usuario.getSenha());
        retornado.setNome(usuario.getNome());
        retornado.setEmail(usuario.getEmail());
        retornado.setIsAtivo(usuario.getIsAtivo());
        retornado.setCodigoAtivacao(usuario.getCodigoAtivacao());
        retornado.setIsAdmin(usuario.getIsAdmin());
        //retornado.setTipoUsuario(usuario.getTipoUsuario());
        retornado.setCpf(usuario.getCpf());
        usuario.setNip(usuario.getNip());
        usuarioRepository.save(usuario);
    }

    @Override
    public Long count() {
        return usuarioRepository.count();
    }

    @Override
    public List<Usuario> getAll(Integer page, Integer size) {
        PageRequest byNome = PageRequest.of(page, size, Sort.by("nome").ascending());
        return usuarioRepository.findAll(byNome).stream().toList();
    }
}
