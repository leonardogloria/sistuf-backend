package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.model.security.SecUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecUsuarioRepository extends JpaRepository<SecUsuario, String> {
    @Query("SELECT u FROM SecUsuario u WHERE u.id = :username OR u.cpf = :username OR u.nip = :username")
    Optional<SecUsuario> findByLoginOrCpfOrNip(@Param("username") String username);


    SecUsuario findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    boolean existsByNip(String nip);
    boolean existsByEmail(String email);
    boolean existsById(String id);

}
