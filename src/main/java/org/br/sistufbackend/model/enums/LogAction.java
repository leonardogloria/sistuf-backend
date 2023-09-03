package org.br.sistufbackend.model.enums;

public enum LogAction {
    INSERT("insert"), DELETE("delete"),
    UPDATE("update"),
    LOGIN_SUCESSO("Login"),
    LOGIN_NAO_REALIZADO("Login Failed");

    private String action;
    LogAction(String action) {
        this.action = action;
    }
}
