package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Navio;
import org.br.sistufbackend.model.dto.NavioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NavioRepository  extends JpaRepository<Navio, Long> {
    List<Navio> findAllByNomeContainsIgnoreCase(String nome);
    @Query("SELECT new org.br.sistufbackend.model.dto.NavioDTO(n.id, n.nome) FROM Navio n ORDER BY n.nome ASC ")
    List<NavioDTO> findAllLazy();

    boolean existsNavioByNumeroNavioIMO(String numeroImo);

}
