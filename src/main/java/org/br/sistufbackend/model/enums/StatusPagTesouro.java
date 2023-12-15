package org.br.sistufbackend.model.enums;

public enum StatusPagTesouro {
    NAO_CRIADO("NAO_CRIADO"),
    CRIADO("CRIADO"),
    INICIADO("INICIADO"),
    SUBMETIDO("SUBMETIDO"),
    CONCLUIDO("CONCLUIDO"),
    REJEITADO("CANCELADO");

    StatusPagTesouro(String situacao) {
        this.situacao = situacao;
    }
    private String situacao;
}
