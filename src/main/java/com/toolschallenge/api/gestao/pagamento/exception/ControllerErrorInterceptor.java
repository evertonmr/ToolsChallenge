package com.toolschallenge.api.gestao.pagamento.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.toolschallenge.api.gestao.pagamento.domain.enums.ErrorType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerErrorInterceptor {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ClientErrorException clientErrorException) {
        ErrorResponse error = ErrorResponse.builder()
            .errorType(clientErrorException.getErrorType())
            .message(clientErrorException.getMessage())
            .details(clientErrorException.getDetails())
            .build();

        log.error("Erro API {}", clientErrorException.getMessage());

        return new ResponseEntity<>(error, clientErrorException.getErrorType().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException dataIntegrityViolationException) {
        final ErrorType errorType = ErrorType.VALIDATION;
        ErrorResponse error = ErrorResponse.builder()
            .errorType(errorType)
            .message(dataIntegrityViolationException.getMessage())
            .build();

        log.error("Erro API {}", dataIntegrityViolationException.getMessage());

        return new ResponseEntity<>(error, errorType.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ServerErrorException serverErrorException) {
        ErrorResponse error = ErrorResponse.builder()
            .errorType(serverErrorException.getErrorType())
            .message(serverErrorException.getMessage())
            .details(serverErrorException.getDetails())
            .build();

        log.error("Erro Interno {}", serverErrorException.getMessage());

        return new ResponseEntity<>(error, serverErrorException.getErrorType().getHttpStatus());
    }

}
