package com.powerreaderapi.powerreaderapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        Optional<FieldError> error = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().findFirst();

        return error.map(
                        fieldError -> String.format("%s", fieldError.getDefaultMessage()))
                .orElse("Error not found");
    }

    //GENERISCS?
    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return entityNotFoundException.getMessage();
    }

    @ExceptionHandler(value = OperationCannotBeCompletedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String operationCannotBeCompletedException(OperationCannotBeCompletedException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String usernameNotFoundException(UsernameNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String badCredentialsException(BadCredentialsException exception) {
        return exception.getMessage();
    }

}
