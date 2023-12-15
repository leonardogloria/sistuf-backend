package org.br.sistufbackend.service;

import org.br.sistufbackend.model.Escala;
import org.br.sistufbackend.model.Roteiro;
import org.br.sistufbackend.validation.exception.EscalaValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ValidarEscalaService {
    @Autowired
    RoteiroService roteiroService;

    public void validarDataSaida(Escala escala) {
        if(escala.getChegada().isAfter(escala.getSaida())){
            throw new EscalaValidationException("A hora de Chegada não pode ser posterior a de saída");
        }
    }

    public void validarRangeDeDatas(Escala escala) {

        Roteiro roteiro = roteiroService.getById(escala.getRoteiro().getId()).get();


        if(isDataChegadaForaDoPeriodo(escala,roteiro)){
            throw new EscalaValidationException("Data de Chegada fora do periodo do roteiro");
        }
        if(isDataSaidaForaDoPeriodo(escala,roteiro)){
            throw new EscalaValidationException("Data de Chegada fora do periodo do roteiro");
        }
    }
    private boolean isDataChegadaForaDoPeriodo(Escala escala, Roteiro roteiro){
        return escala.getChegada().isBefore(roteiro.getEntrada()) || escala.getChegada().isAfter(roteiro.getSaida());
    }
    private boolean isDataSaidaForaDoPeriodo(Escala escala, Roteiro roteiro){
        return escala.getSaida().isBefore(roteiro.getEntrada()) || escala.getSaida().isAfter(roteiro.getSaida());
    }
    public void validarQuantidadeDePortos(Escala escala, List<Escala> escalas) {
        Roteiro roteiro = roteiroService.getById(escala.getRoteiro().getId()).get();
        Integer quantidadePortos = roteiro.getQuantidadePortos();
        int quantidadeDeEscalas = escalas.size();
        if(quantidadeDeEscalas >= quantidadePortos){
            throw new EscalaValidationException("Quantidade de Portos limitada. Verifique a quantidade de Portos permitida no Roteiro!");
        }
    }
    public void validarSeEhUltimaEscala(Escala escala, List<Escala> escalas){
        if(!isLastEscala(escala,escalas)){
            throw new EscalaValidationException("Não é permitida a manipulação de uma escala que não seja a última!");
        }
    }
    private boolean isLastEscala(Escala escala, List<Escala> escalas){
        List<Escala> list = escalas.stream().sorted(Comparator.comparing(Escala::getChegada)).toList();
        if(list.get(list.size() - 1).getId().equals(escala.getId())){
           return true;
       }else return false;
    }
}
