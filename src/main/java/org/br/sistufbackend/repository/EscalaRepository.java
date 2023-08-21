package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Escala;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscalaRepository extends JpaRepository<Escala,Long> {
    List<Escala> findAllByRoteiroId(Long roteiroId, Sort sort);
}
