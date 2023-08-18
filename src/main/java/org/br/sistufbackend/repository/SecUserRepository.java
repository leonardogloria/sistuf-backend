package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecUserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.login = :username OR u.cpf = :username OR u.nip = :username")
    Optional<User> findByLoginOrCpfOrNip(@Param("username") String username);
}
