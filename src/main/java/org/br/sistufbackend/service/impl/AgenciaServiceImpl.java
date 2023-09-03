package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.mapper.UsuarioMapper;
import org.br.sistufbackend.model.Agencia;
import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.repository.AgenciaRepository;
import org.br.sistufbackend.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgenciaServiceImpl implements AgenciaService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AgenciaRepository agenciaRepository;

    public AgenciaServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Agencia save(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

    @Override
    public Optional<Agencia> getById(Long id) {
        return agenciaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        agenciaRepository.deleteById(id);
    }

    @Override
    public List<Agencia> getAll() {
        return agenciaRepository.findAll();
    }

    @Override
    public void update(Long id, Agencia agencia) {
        Agencia retornada = agenciaRepository.findById(id).get();
        retornada.setBairro(agencia.getBairro());
        retornada.setCep(agencia.getCep());
        retornada.setCnpj(agencia.getCnpj());
        retornada.setCidade(agencia.getCidade());
        retornada.setContato(agencia.getContato());
        retornada.setEmail(agencia.getEmail());
        retornada.setEstado(agencia.getEstado());
        retornada.setNome(agencia.getNome());
        retornada.setComplemento(agencia.getComplemento());
        retornada.setNumero(agencia.getNumero());
        retornada.setLogradouro(agencia.getLogradouro());
        retornada.setTelefone(agencia.getTelefone());
        retornada.setRamal(agencia.getRamal());
        retornada.setStatus(agencia.getStatus());
        agenciaRepository.save(retornada);
    }

    @Override
    public Long count() {
        return agenciaRepository.count();
    }

    @Override
    public List<Agencia> findByNome(String nome) {
        return agenciaRepository.findAllByNomeContainsIgnoreCase(nome);
    }

    @Override
    public List<Agencia> getAll(Integer size, Integer page) {
        PageRequest of = PageRequest.of(page, size);
        return agenciaRepository.findAll(of).stream().toList();
    }

    @Override
    public List<Usuario> findAllByAgenciaId(Long id) {
        String sql = """
               SELECT u.* FROM  sec_users_agencia ua JOIN  sec_users u  ON  ua.sec_users_login = u.login 
                WHERE ua.agencia_idagencia = ?;
        """;
        return jdbcTemplate.query(sql, new UsuarioMapper(), new Object[]{id});
    }

    @Override
    public Long findAgenciaDoUsuarioById(String id) {
        String query = """
                SELECT a.idagencia FROM sec_users_agencia ua JOIN agencia a ON ua.agencia_idagencia = a.idagencia
                    WHERE ua.sec_users_login =?;
                """;
        return jdbcTemplate.queryForObject(query,Long.class,new Object[]{ id});
    }

    @Override
    public void associaUsuarioAAgencia(String login, Long agenciaId) {
        String sql = """
                INSERT into sec_users_agencia (sec_users_login, agencia_idagencia, data_inicio, data_fim)
                VALUES (?,?, ? , ?);
                """;
        jdbcTemplate.update(sql,login,agenciaId, LocalDateTime.now(), LocalDateTime.now().plusYears(1));
    }


    @Override
    public boolean isUsuarioInAgencia(String id) {
        boolean isInAgencia = false;
        try{
            findAgenciaDoUsuarioById(id);
            isInAgencia =  true;
        }catch (EmptyResultDataAccessException ignored){}
        return isInAgencia;
    }
}
