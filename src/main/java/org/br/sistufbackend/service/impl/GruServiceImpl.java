package org.br.sistufbackend.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.RandomStringUtils;
import org.br.sistufbackend.model.*;
import org.br.sistufbackend.model.dto.CobrancaDTO;
import org.br.sistufbackend.model.payload.GRUFilters;
import org.br.sistufbackend.model.payload.PagTesouroResponsePayload;
import org.br.sistufbackend.repository.EscalaRepository;
import org.br.sistufbackend.repository.GRURepository;
import org.br.sistufbackend.repository.GruIsentaRepository;
import org.br.sistufbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GruServiceImpl implements GruService {
    @Autowired
    GRURepository gruRepository;

    @Autowired
    EscalaRepository escalaRepository;
    @Autowired
    NavioService navioService;
    @Autowired
    RoteiroService roteiroService;
    @Autowired
    ValorTufService valorTufService;

    @Autowired
    PagTesouroService pagTesouroService;
    @Autowired
    EntityManager entityManager;
    @Autowired
    private GruIsentaRepository gruIsentaRepository;


    @Override
    public GRU criarComPagTesouro(GRU gru) {
        GRU created = save(gru);

        CobrancaDTO cobranca = CobrancaDTO.builder().referencia(gru.getId().toString())
                .vencimento(gru.getVencimento().format(DateTimeFormatter.ofPattern("ddMMyyyy")))
                .nomeContribuinte("PAGAMENTO DE TUF")
                .valorPrincipal(gru.getValorReal()).build();
        try{
            PagTesouroResponsePayload ptPayload = pagTesouroService.gerarCobranca(cobranca);
            created.setIdPagTesouro(ptPayload.getIdPagamento());
            created.setDpcProximaURL(ptPayload.getProximaUrl());
            created.setPagTesouroStatus(ptPayload.getSituacao().getCodigo().name());
            gruRepository.save(created);
        }catch (RuntimeException ex){
            ex.printStackTrace();
            delete(gru.getId());
            throw new RuntimeException("Erro Ao criar PAGAMENTO no PAGTESOURO");
        }
        return created;
    }
    public GRU save(GRU gru) {

        Escala escala = escalaRepository.findById(gru.getEscala().getId()).get();
        Roteiro roteiro = roteiroService.getById(escala.getRoteiro().getId()).get();
        Navio navio = navioService.getById(roteiro.getNavio().getId()).get();
        ValorTUF byTonelagem = valorTufService.getByTonelagem(navio.getTonelagemPesoBruto());
        gru.setValorTUF(byTonelagem);
        LocalDate hoje = LocalDate.now();
        LocalDate daquiHa15Dias = hoje.plusDays(15);
        gru.setEmissao(hoje);
        gru.setVencimento(daquiHa15Dias);
        gru.setStatus('1');
        gru.setPagTesouroStatus("NAO_CRIADO");
        gru.setNossoNumero(generateNossoNumero());
        return gruRepository.save(gru);
    }
    private String generateNossoNumero(){
        int length = 16;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);

    }
    @Override
    public Optional<GRU> getById(Long id) {
        return gruRepository.findById(id);
    }

    @Override
    public Optional<GRU> findByEscalaId(Long id) {
        return gruRepository.findByEscalaId(id);
    }

    @Override
    public void update(GRU gru) {
        gruRepository.save(gru);
    }

    @Override
    public List<GRU> getWithFilters(GRUFilters filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GRU> cq = cb.createQuery(GRU.class);
        Root<GRU> gru = cq.from(GRU.class);
        List<Predicate> predicates = new ArrayList<>();
        if(filters.getStatus().isPresent()){
            Predicate pagTesouroStatus = cb.equal(gru.get("pagTesouroStatus"), filters.getStatus().get());
            predicates.add(pagTesouroStatus);
        }
        if(filters.getEmissaoStart().isPresent()){
            Predicate emissao = cb.between(gru.get("emissao"), filters.getEmissaoStart().get(), filters.getEmissaoEnd().orElse(LocalDate.now()));
            predicates.add(emissao);
        }
        if(filters.getVencimentoStart().isPresent()){
            Predicate vencimento = cb.between(gru.get("vencimento"), filters.getVencimentoStart().get(), filters.getVencimentoEnd().orElse(LocalDate.now()));
            predicates.add(vencimento);
        }

        cq.where(predicates.toArray(Predicate[]::new));
        TypedQuery<GRU> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<GRUIsenta> getIsentaWithFilters(GRUFilters filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GRUIsenta> cq = cb.createQuery(GRUIsenta.class);
        Root<GRUIsenta> gru = cq.from(GRUIsenta.class);
        List<Predicate> predicates = new ArrayList<>();

        if(filters.getEmissaoStart().isPresent()){
            Predicate emissao = cb.between(gru.get("emissao"), filters.getEmissaoStart().get(), filters.getEmissaoEnd().orElse(LocalDate.now()));
            predicates.add(emissao);
        }
        if(filters.getVencimentoStart().isPresent()){
            Predicate vencimento = cb.between(gru.get("vencimento"), filters.getVencimentoStart().get(), filters.getVencimentoEnd().orElse(LocalDate.now()));
            predicates.add(vencimento);
        }

        cq.where(predicates.toArray(Predicate[]::new));
        TypedQuery<GRUIsenta> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void isentarGru(Long id) {
        GRU gru = gruRepository.findById(id).get();
        GRUIsenta gruIsenta = criarGruIsenta(gru);
        gruIsentaRepository.save(gruIsenta);
        gruRepository.deleteById(id);

        //GRU gru = gruRepository.findById(id).get();
        //gru.setStatus('9');
        //gru.setPagTesouroStatus("ISENTA");
        //gruRepository.save(gru);

    }

    @Override
    public void delete(long id) {
        gruRepository.deleteById(id);
    }
    private GRUIsenta criarGruIsenta(GRU gru){
        return
                GRUIsenta.builder().dpcProximaURL(gru.getDpcProximaURL()).pagTesouroProximaURL(gru.getPagTesouroProximaURL())
                .idPagTesouro(gru.getIdPagTesouro()).pago(gru.getPago()).credito(gru.getCredito()).pagTesouroStatus("ISENTA")
                .quitacao(gru.getQuitacao()).tarifa(gru.getTarifa()).nossoNumero(gru.getNossoNumero()).tipoPagamentoEscolhido(gru.getTipoPagamentoEscolhido())
                .valorReal(gru.getValorReal()).valorTUF(gru.getValorTUF()).status((char) 9).emissao(gru.getEmissao()).escala(gru.getEscala()).build();
    }
}
