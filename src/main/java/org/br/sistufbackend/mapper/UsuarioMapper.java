package org.br.sistufbackend.mapper;

import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.model.security.SecUsuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        String login = rs.getString("login");
        String cpf = rs.getString("cpf");
        String nome = rs.getString("name");
        String email = rs.getString("email");
        char  isAtivo = rs.getString("active").charAt(0);
        String codigoAtivacao = rs.getString("activation_code");
        char isAdmin = rs.getString("priv_admin").charAt(0);
        String nip = rs.getString("nip");


        return Usuario.builder().id(login)
                .cpf(cpf)
                .id(login)
                .nip(nip)
                .nome(nome)
                .email(email)
                .isAdmin(isAdmin)
                .codigoAtivacao(codigoAtivacao)
                .isAtivo(isAtivo)
                .build();

    }
}
