package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,String> , PagingAndSortingRepository<Estado, String> {
    List<Estado> findAllByUfLikeIgnoreCase(String nome);
    List<Estado> findAllByNomeContainsIgnoreCase(String nome);
}
