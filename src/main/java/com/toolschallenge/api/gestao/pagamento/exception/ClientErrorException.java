package com.toolschallenge.api.gestao.pagamento.exception;


import com.toolschallenge.api.gestao.pagamento.domain.enums.ErrorType;

public class ClientErrorException extends AbstractErrorException {

    private static final long serialVersionUID = 1486297407038116871L;

    private final ErrorType errorType;

    public ClientErrorException(ErrorType errorType, String msg) {
        super(msg);
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }

}
