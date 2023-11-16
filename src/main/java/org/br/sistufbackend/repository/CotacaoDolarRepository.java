package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.CotacaoDolar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface CotacaoDolarRepository extends JpaRepository<CotacaoDolar, LocalDate>, PagingAndSortingRepository<CotacaoDolar, LocalDate> {
    List<CotacaoDolar> getCotacaoDolarByDataBeforeOrderByDataDesc(LocalDate hoje, Pageable size);
}
