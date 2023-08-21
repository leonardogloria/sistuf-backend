package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario,Long> {
    List<GrupoUsuario> findAllByDescricaoIgnoreCase(String descricao);
    boolean existsByDescricao(String descricao);
}
