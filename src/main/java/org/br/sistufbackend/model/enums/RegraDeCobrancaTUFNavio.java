package org.br.sistufbackend.model.enums;

public enum RegraDeCobrancaTUFNavio {
    NAO_PAGA("Não Paga"),
    DOIS_PRIMEIROS_E_DOIS_ULTIMOS("Dois primeiros e dois ultimo"),
    PORTOS_IMPARES_MESMO_ESTADO("Portos Impares no mesmo estado"),
    UMA_VEZ_A_CADA_90_DIAS("Uma vez a cada 90 dias."),
    TODOS_OS_PORTOS_NACIONAIS_VISITADOS("Todos os portos nacionais visitados."),

    TESTE("Teste");
    private String regra;
    RegraDeCobrancaTUFNavio(String regra){
        this.regra  = regra;
    }
}
