package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.CotacaoDolar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CotacaoDolarRepository extends JpaRepository<CotacaoDolar, LocalDate> {
}
