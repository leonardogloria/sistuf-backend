package org.br.sistufbackend.exception;

public class UsuarioValidationException extends RuntimeException{
    public UsuarioValidationException(String message){
        super(message);
    }
}
