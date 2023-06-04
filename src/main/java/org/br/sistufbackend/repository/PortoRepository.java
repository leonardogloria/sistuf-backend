package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Porto;
import org.br.sistufbackend.model.dto.NavioDTO;
import org.br.sistufbackend.model.dto.PortoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortoRepository extends JpaRepository<Porto,Long> {
    @Query("SELECT new org.br.sistufbackend.model.dto.PortoDTO(p.id, p.nome) FROM Porto p ORDER BY p.nome ASC ")
    List<PortoDTO> findAllLazy();
    List<Porto> findAllByNomeContainsIgnoreCase(String nome);
}
