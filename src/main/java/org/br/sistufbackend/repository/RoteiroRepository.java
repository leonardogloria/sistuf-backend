package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Roteiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoteiroRepository extends JpaRepository<Roteiro, Long> {

}
