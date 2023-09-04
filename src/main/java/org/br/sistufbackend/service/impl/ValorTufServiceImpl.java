package org.br.sistufbackend.service.impl;

import org.br.sistufbackend.exception.TonelagemValidationException;
import org.br.sistufbackend.model.ValorTUF;
import org.br.sistufbackend.repository.ValorTufRepository;
import org.br.sistufbackend.service.ValorTufService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ValorTufServiceImpl implements ValorTufService {
    @Autowired
    ValorTufRepository valorTufRepository;
    @Override
    public List<ValorTUF> getAll() {
        return valorTufRepository.findAll();
    }
    public Long count(){
        return valorTufRepository.count();
    }
    @Override
    public ValorTUF create(ValorTUF valorTUF) {
        validaIsencao(valorTUF);
        validaIntervalo(valorTUF);
        return valorTufRepository.save(valorTUF);
    }

    private void validaIsencao(ValorTUF valorTUF) {
        if(valorTUF.getValorDolar().compareTo(BigDecimal.ZERO) == 0 && valorTUF.getCategoriaVisita() == null){
            throw new TonelagemValidationException("O preenchimento da Categoria de Visita é obrigatório para Isenção");
        }
        if(valorTUF.getValorDolar().compareTo(BigDecimal.ZERO) != 0 && valorTUF.getCategoriaVisita() != null){
            throw new TonelagemValidationException("O preenchimento da Categoria de Visita só é permitido para Isenção");
        }
    }

    @Override
    public void deleteById(Long id) {
        valorTufRepository.deleteById(id);
    }

    @Override
    public ValorTUF getById(long id) {
        return valorTufRepository.findById(id).get();
    }

    private void validaIntervalo(ValorTUF valorTUF){
        List<ValorTUF> all = valorTufRepository.findAll();
         all
                .stream()
                .filter(tuf -> tonelagemInicialExiste(tuf, valorTUF))
                .findAny()
                 .ifPresent(valor -> {
                     throw new TonelagemValidationException("A TPB inicial coincide com outro intervalo!");
                 });
        all
                .stream()
                .filter(tuf -> tonelagemFinalExiste(tuf, valorTUF))
                .findAny()
                .ifPresent(valor -> {
                    throw new TonelagemValidationException("A TPB final coincide com outro intervalo!");
                });

    }
    private boolean tonelagemInicialExiste(ValorTUF doBanco, ValorTUF enviado){
        return (doBanco.getTonelagemPesoBrutoInicial() <= enviado.getTonelagemPesoBrutoInicial()
                && (doBanco.getTonelagemPesoBrutoFinal() >= enviado.getTonelagemPesoBrutoInicial())
        );
    }
    private boolean tonelagemFinalExiste(ValorTUF doBanco, ValorTUF enviado){
        return (doBanco.getTonelagemPesoBrutoInicial() <= enviado.getTonelagemPesoBrutoFinal()
                && (doBanco.getTonelagemPesoBrutoFinal() >= enviado.getTonelagemPesoBrutoFinal())
        );
    }

}
