package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.*;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUF;
import org.br.sistufbackend.repository.EscalaRepository;
import org.br.sistufbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class EscalaServiceImpl implements EscalaService {
    @Autowired
    EscalaRepository escalaRepository;
    @Autowired
    ValidarEscalaService validarEscalaService;
    @Autowired
    RoteiroService roteiroService;
    @Autowired
    NavioService navioService;
    @Autowired
    PaisService paisService;
    @Autowired
    ValorTufService valorTufService;
    @Autowired
    CotacaoDolarService cotacaoDolarService;
    @Autowired
    TipoNavioService tipoNavioService;
    @Autowired
    RazaoDeVisitaService razaoDeVisitaService;



    @Override
    public Escala save(Escala escala) {
        validarEscalaService.validarDataSaida(escala);
        validarEscalaService.validarQuantidadeDePortos(escala, getAllByRoteiroId(escala.getRoteiro().getId()));
        validarEscalaService.validarRangeDeDatas(escala);

        //return escalaRepository.save(escala);
        return null;
    }
    private ValorTUF getValorTufFromNavio(Navio navio){
        List<ValorTUF> all = valorTufService.getAll();
        return  all.stream()
                .filter(valorTUF -> navio.getTonelagemPesoBruto() >= valorTUF.getTonelagemPesoBrutoInicial() &&
                        navio.getTonelagemPesoBruto() <= valorTUF.getTonelagemPesoBrutoFinal())
                .findFirst().get();
    }
    private boolean navioNaoEhBrasileiro(Pais pais){
        return !pais.getCodigoPaisAlpha2().equals("BR");
    }

    @Override
    public Optional<Escala> getById(Long id) {
        return escalaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Escala escala = getById(id).get();
        validarEscalaService.validarSeEhUltimaEscala(escala, getAllByRoteiroId(escala.getRoteiro().getId()));
        escalaRepository.deleteById(id);
    }
    @Override
    public List<Escala> getAll() {
        return escalaRepository.findAll();
    }

    @Override
    public void update(Long id, Escala escala) {
        Escala escalaExistente = escalaRepository.findById(id).get();

        escalaExistente.setAgencia(escala.getAgencia());
        escalaExistente.setPorto(escala.getPorto());
        escalaExistente.setCategoriaVisita(escala.getCategoriaVisita());
        escalaExistente.setRazaoDeVisita(escala.getRazaoDeVisita());
        escalaExistente.setChegada(escala.getChegada());
        escalaExistente.setSaida(escala.getSaida());
        validarEscalaService.validarDataSaida(escalaExistente);
        validarEscalaService.validarRangeDeDatas(escalaExistente);
        validarEscalaService.validarSeEhUltimaEscala(escalaExistente, getAllByRoteiroId(escalaExistente.getRoteiro().getId()));

        escalaRepository.save(escalaExistente);


    }

    @Override
    public Long count() {
        return escalaRepository.count();
    }

    @Override
    public List<Escala> getAll(Integer page, Integer size) {
        PageRequest of = PageRequest.of(page, size);
        return escalaRepository.findAll(of).stream().toList();
    }

    @Override
    public List<Escala> getAllByRoteiroId(Long id) {
        Sort order = Sort.by(Sort.Direction.DESC, "saida");
        return escalaRepository.findAllByRoteiroId(id,order);
    }

    @Override
    public void verificarDebito(Long id) {
        Escala escala = getById(id).get();
        Roteiro roteiro = roteiroService.getById(escala.getRoteiro().getId()).get();
        Navio navio = navioService.getById(roteiro.getNavio().getId()).get();
        Pais pais = paisService.getById(navio.getPais().getId()).get();
        TipoNavio tipoNavio = tipoNavioService.getById(navio.getTipoNavio().getId()).get();
        RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(escala.getRazaoDeVisita().getId()).get();
        if(navioNaoEhBrasileiro(pais)){
            if(pais.getAcordoComBrasil().equals("0") || navio.getPagaIndependenteReciprocidade().equals("1") ){
                RegraDeCobrancaTUF regraDeCobrancaTUF = razaoDeVisita.getRegraDeCobrancaTUF();
                if(regraDeCobrancaTUF.equals(RegraDeCobrancaTUF.PAGA_SEMPRE)){
                    ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                    CotacaoDolar last = cotacaoDolarService.getLast();
                    BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                    //TODO salvar GRU
                }else if(regraDeCobrancaTUF.equals(RegraDeCobrancaTUF.DEPENDE_NAVIO)){
                    int ordinal = tipoNavio.getRegraDeCobrancaTUF().ordinal();
                    if(ordinal == 1){ //Dois primeiros e dois ultimos portos
                        List<Escala> escalasDoRoteiro = getAllByRoteiroId(roteiro.getId());
                        int indexOfEscala = escalasDoRoteiro.indexOf(escala);
                        int quantidadeEscalas = escalasDoRoteiro.size();
                        boolean pagaGru = indexOfEscala == 0 || indexOfEscala == 1 || indexOfEscala == quantidadeEscalas - 1 || indexOfEscala == quantidadeEscalas - 2;
                        if(pagaGru){
                            CotacaoDolar last = cotacaoDolarService.getLast();
                            ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                            BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                            //TODO salvar GRU
                        }


                    }else if (ordinal == 2){ //Portos impares no mesmo estado
                        List<Escala> escalasDoRoteiro = getAllByRoteiroId(roteiro.getId());
                        int indexOfEscala = escalasDoRoteiro.indexOf(escala);
                        if(indexOfEscala % 2 != 0 ){
                            CotacaoDolar last = cotacaoDolarService.getLast();
                            ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                            BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                            //TODO salvar GRU
                        }



                    }else if( ordinal == 3){ //A cada 90 dias

                    }else if(ordinal == 4){// Todos os portos do pais

                    }else { // Regra == 0 -> nao paga

                    }


                }

            }
        }



    }
}
