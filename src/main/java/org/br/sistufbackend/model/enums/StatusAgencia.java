package org.br.sistufbackend.model.enums;

public enum StatusAgencia {
    ATIVO(1),
    INATIVO(0);
    private Integer status;
    StatusAgencia(Integer status) {
        this.status = status;
    }
}
