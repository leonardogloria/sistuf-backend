package org.br.sistufbackend.model.enums;

public enum StatusAgencia {
    ATIVO("Ativo"),
    INATIVO("Inativo");
    private String status;
    StatusAgencia(String status) {
        this.status = status;
    }
}
