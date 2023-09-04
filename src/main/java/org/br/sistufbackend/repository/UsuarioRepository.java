package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    Usuario findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    boolean existsByNip(String nip);
    boolean existsByEmail(String email);
    boolean existsById(String id);


}
