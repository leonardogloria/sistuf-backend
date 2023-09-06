package org.br.sistufbackend.repository;

import org.br.sistufbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    Usuario findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    boolean existsByNip(String nip);
    boolean existsByEmail(String email);
    boolean existsById(String id);

    List<Usuario> findUsuariosByNomeOrCpfOrId(String nome, String cpf, String id);
    List<Usuario> findUsuariosByIdOrNipOrCpf(String login,String nip,String cpf);


}
