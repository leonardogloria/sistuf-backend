package org.br.sistufbackend.model.enums;

public enum RegraDeCobrancaTUF {
    NAO_PAGA("Não Paga"),
    PAGA_SEMPRE("Paga Sempre"),
    DEPENDE_NAVIO("Dependente do tipo do navio");
    private String regra;
    RegraDeCobrancaTUF(String regra){
        this.regra  = regra;
    }
}
