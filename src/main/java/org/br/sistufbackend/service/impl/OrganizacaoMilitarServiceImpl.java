package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.mapper.UsuarioMapper;
import org.br.sistufbackend.model.OrganizacaoMilitar;
import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.repository.OrganizacaoMilitarRepositoty;
import org.br.sistufbackend.service.OrganizacaoMilitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizacaoMilitarServiceImpl implements OrganizacaoMilitarService {
    @Autowired
    OrganizacaoMilitarRepositoty omRepositoty;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<OrganizacaoMilitar> getAll() {
        Sort sort = Sort.by("nome").ascending();
        return omRepositoty.findAll(sort).stream().toList();
    }

    @Override
    public Optional<OrganizacaoMilitar> getById(Long id) {
        return omRepositoty.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        omRepositoty.deleteById(id);
    }

    @Override
    public OrganizacaoMilitar save(OrganizacaoMilitar organizacaoMilitar) {
        return omRepositoty.save(organizacaoMilitar);
    }

    @Override
    public void update(Long id, OrganizacaoMilitar om) {
        OrganizacaoMilitar retornada = omRepositoty.findById(id).get();
        retornada.setBairro(om.getBairro());
        retornada.setCep(om.getCep());
        retornada.setCidade(om.getCidade());
        retornada.setEmail(om.getEmail());
        retornada.setEstado(om.getEstado());
        retornada.setComplemento(om.getComplemento());
        retornada.setNome(om.getNome());
        retornada.setNumero(om.getNumero());
        retornada.setSigla(om.getSigla());
        retornada.setSistelma(om.getSistelma());
        retornada.setEstado(om.getEstado());
        retornada.setIndicativo(om.getIndicativo());
        retornada.setLogradouro(om.getLogradouro());
        retornada.setCnpj(om.getCnpj());
        retornada.setCodigo(om.getCodigo());
        omRepositoty.save(retornada);

    }

    @Override
    public Long count() {
        return omRepositoty.count();
    }

    @Override
    public List<OrganizacaoMilitar> findByNome(String nome) {
        return omRepositoty.findAllByNomeContainsIgnoreCase(nome);
    }

    @Override
    public List<OrganizacaoMilitar> getAll(Integer size, Integer page) {
        PageRequest of = PageRequest.of(page, size);
        return omRepositoty.findAll(of).stream().toList();

    }

    @Override
    public Long findOrganizacaoDoUsuarioById(String login) {
        String query = """
                        SELECT orgmil_idorgmil FROM sec_users_orgmil 
                            WHERE sec_users_login =? ;
                """;
        return jdbcTemplate.queryForObject(query,Long.class,new Object[]{ login});
    }

    @Override
    public List<Usuario> findAllUsuariosByOrgMilId(Long id) {
        String sql = """
                SELECT u.* FROM  sec_users_orgmil ua JOIN  sec_users u  ON  ua.sec_users_login = u.login
                              WHERE ua.orgmil_idorgmil = ?;
        """;
        return jdbcTemplate.query(sql, new UsuarioMapper(), new Object[]{id});
    }

    @Override
    public void associarUsuarioAOrganizacao(String login, Long organizacao) {
        String sql = """
                INSERT INTO
                 sec_users_orgmil(orgmil_idorgmil, sec_users_login, vigencia) 
                 VALUES 
                 (?,?,? );
                            
                """;
        LocalDateTime localDateTime = LocalDateTime.now().plusYears(1);
        jdbcTemplate.update(sql,organizacao,login,localDateTime);
    }

    @Override
    public boolean isUsuarioInOrgMil(String login) {
        boolean isInOrg = false;
        try{
            verifyIfUserInOrgMil(login);
            isInOrg = true;
        }catch (EmptyResultDataAccessException ignored){}
        return isInOrg;

    }
    private Integer verifyIfUserInOrgMil(String login){
        String sql = """
                SELECT orgmil_idorgmil FROM sec_users_orgmil WHERE sec_users_login =?;
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{login}, Integer.class);
    }
}
