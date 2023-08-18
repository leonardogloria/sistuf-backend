package org.br.sistufbackend.model.enums;

public enum YesNo {

    N, Y;

    public boolean isTrue() {
        return this.equals(Y);
    }
}
