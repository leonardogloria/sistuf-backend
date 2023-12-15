package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.model.*;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUF;
import org.br.sistufbackend.model.enums.RegraDeCobrancaTUFNavio;
import org.br.sistufbackend.model.payload.PagTesouroResponsePayload;
import org.br.sistufbackend.repository.EscalaRepository;
import org.br.sistufbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
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
    @Autowired
    PagTesouroService pagTesouroService;
    @Autowired
    GruService gruService;
    @Autowired
    PortoService portoService;



    @Override
    public Escala save(Escala escala) {
        validarEscalaService.validarDataSaida(escala);
        validarEscalaService.validarQuantidadeDePortos(escala, getAllByRoteiroId(escala.getRoteiro().getId()));
        validarEscalaService.validarRangeDeDatas(escala);

        Escala saved = escalaRepository.save(escala);
     //   criarGru(saved.getId());
        return saved;
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
    public Optional<GRU> verificarDebito(Long id) {

        Optional<GRU> byEscalaId = gruService.findByEscalaId(id);
        if(byEscalaId.isEmpty()) return Optional.empty();
        GRU gru = byEscalaId.get();
        PagTesouroResponsePayload response =
                pagTesouroService.verificaStatusPagamento(gru.getIdPagTesouro());
        gru.setPagTesouroStatus(response.getSituacao().getCodigo().name());
        if(response.getSituacao().getCodigo().name().equals("CONCLUIDO") && gru.getQuitacao() == null ){
            gru.setQuitacao(response.getSituacao().getData().toLocalDate());
            gruService.save(gru);
        }

        return Optional.of(gru);
    }
    public void criarGru(List<Long> ids){
           ids.forEach(this::criarGru);
        Escala escala = escalaRepository.findById(ids.get(0)).get();
        Roteiro roteiro = roteiroService.getById(escala.getRoteiro().getId()).get();
        roteiro.setGrusGeradas(true);
        roteiroService.save(roteiro);
    }

    private void criarGru(Long id) {
        Escala escala = getById(id).get();
        Roteiro roteiro = roteiroService.getById(escala.getRoteiro().getId()).get();
        Navio navio = navioService.getById(roteiro.getNavio().getId()).get();
        Pais pais = paisService.getById(navio.getPais().getId()).get();
        TipoNavio tipoNavio = tipoNavioService.getById(navio.getTipoNavio().getId()).get();
        Porto porto = portoService.getById(escala.getPorto().getId()).get();
        RazaoDeVisita razaoDeVisita = razaoDeVisitaService.getById(escala.getRazaoDeVisita().getId()).get();
        if(navioNaoEhBrasileiro(pais)){
            if(pais.getAcordoComBrasil().equals("0") || navio.getPagaIndependenteReciprocidade().equals("1") ){
                RegraDeCobrancaTUF regraDeCobrancaTUF = razaoDeVisita.getRegraDeCobrancaTUF();
                if(regraDeCobrancaTUF.equals(RegraDeCobrancaTUF.PAGA_SEMPRE)){
                    ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                    CotacaoDolar last = cotacaoDolarService.getLast();
                    BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                    GRU gru = GRU.builder().escala(escala).valorReal(valorGRU).build();
                    gruService.criarComPagTesouro(gru);
                }else if(regraDeCobrancaTUF.equals(RegraDeCobrancaTUF.DEPENDE_NAVIO)){
                    int ordinal = tipoNavio.getRegraDeCobrancaTUF().ordinal();
                    if(tipoNavio.getRegraDeCobrancaTUF().equals(RegraDeCobrancaTUFNavio.DOIS_PRIMEIROS_E_DOIS_ULTIMOS)){ //Dois primeiros e dois ultimos portos
                        List<Escala> escalasDoRoteiro = getAllByRoteiroId(roteiro.getId());
                        escalasDoRoteiro = escalasDoRoteiro.stream().sorted(Comparator.comparing(Escala::getChegada)).toList();
                        int indexOfEscala = escalasDoRoteiro.indexOf(escala);
                        int quantidadeEscalas = escalasDoRoteiro.size();
                        boolean pagaGru = indexOfEscala == 0 || indexOfEscala == 1 || indexOfEscala == quantidadeEscalas - 1 || indexOfEscala == quantidadeEscalas - 2;
                        if(pagaGru){
                            CotacaoDolar last = cotacaoDolarService.getLast();
                            ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                            BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                            GRU gru = GRU.builder().escala(escala).valorReal(valorGRU).build();
                            gruService.criarComPagTesouro(gru);
                        }


                    }else if (tipoNavio.getRegraDeCobrancaTUF().equals(RegraDeCobrancaTUFNavio.PORTOS_IMPARES_MESMO_ESTADO)){ //Portos impares no mesmo estado
                        boolean pagaGru = false;
                        List<Escala> escalasDoRoteiro = getAllByRoteiroId(roteiro.getId());
                        escalasDoRoteiro = escalasDoRoteiro.stream().sorted(Comparator.comparing(Escala::getChegada)).toList();

                        int indexOfEscala = escalasDoRoteiro.indexOf(escala);
                        if(ehOPrimeiroPorto(indexOfEscala)){
                            Estado estadoPortoOrigem = roteiro.getPortoOrigem().getEstado();
                            Estado estadoEscala = porto.getEstado();
                            if(portosDoMesmoEstado(estadoPortoOrigem, estadoEscala)){
                                pagaGru = true;
                            }
                        }else if(ehPortoImpar(indexOfEscala)){
                            Estado estadoDoPortoDaEscala = portoService.getById(escala.getPorto().getId()).get().getEstado();
                            Estado estadoDoPortoAnterior = portoService.getById(escalasDoRoteiro.get(indexOfEscala - 1).getPorto().getId()).get().getEstado();

                            if( portosDoMesmoEstado(estadoDoPortoDaEscala, estadoDoPortoAnterior)){
                                pagaGru = true;
                            }
                        }
                        if(pagaGru){
                            CotacaoDolar last = cotacaoDolarService.getLast();
                            ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                            BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                            GRU gru = GRU.builder().escala(escala).valorReal(valorGRU).build();
                            gruService.criarComPagTesouro(gru);
                        }



                    }else if( tipoNavio.getRegraDeCobrancaTUF().equals(RegraDeCobrancaTUFNavio.UMA_VEZ_A_CADA_90_DIAS)){ //A cada 90 dias



                    }else if(tipoNavio.getRegraDeCobrancaTUF().equals(RegraDeCobrancaTUFNavio.TODOS_OS_PORTOS_NACIONAIS_VISITADOS)){// Todos os portos do pais
                        CotacaoDolar last = cotacaoDolarService.getLast();
                        ValorTUF valorTufFromNavio = getValorTufFromNavio(navio);
                        BigDecimal valorGRU = valorTufFromNavio.getValorDolar().multiply(last.getVenda());
                        GRU gru = GRU.builder().escala(escala).valorReal(valorGRU).build();
                        gruService.criarComPagTesouro(gru);


                    }else { // Regra == 0 -> nao paga

                    }


                }

            }
        }



    }

    private static boolean ehPortoImpar(int indexOfEscala) {
        return (indexOfEscala + 1) % 2 != 0;
    }

    private static boolean ehOPrimeiroPorto(int indexOfEscala) {
        return (indexOfEscala + 1) == 1;
    }

    private static boolean portosDoMesmoEstado(Estado estadoPortoOrigem, Estado estadoEscala) {
        return estadoPortoOrigem.getUf().equals(estadoEscala.getUf());
    }
}
