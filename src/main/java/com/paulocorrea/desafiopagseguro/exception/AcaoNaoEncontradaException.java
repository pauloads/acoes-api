package com.paulocorrea.desafiopagseguro.exception;

public class AcaoNaoEncontradaException extends RuntimeException {
    public AcaoNaoEncontradaException() {
        super();
    }

    public AcaoNaoEncontradaException(String message) {
        super(message);
    }
}
