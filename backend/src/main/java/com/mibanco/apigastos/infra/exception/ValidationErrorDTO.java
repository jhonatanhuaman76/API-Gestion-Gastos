package com.mibanco.apigastos.infra.exception;

import org.springframework.validation.FieldError;

public record ValidationErrorDTO(
        String field,
        String errorMessage
) {
    public ValidationErrorDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
