package com.toolschallenge.api.gestao.pagamento.domain.enuns;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

    BUSINESS(HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    VALIDATION(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

}
