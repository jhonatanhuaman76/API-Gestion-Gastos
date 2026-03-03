package com.mibanco.apigastos.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> invalidDTO(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream().map(ValidationErrorDTO::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<List<ValidationErrorDTO>> customException(AppException e){
        String message = e.getMessage();
        ValidationErrorDTO error = new ValidationErrorDTO("error", message);
        return ResponseEntity.badRequest().body(List.of(error));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ValidationErrorDTO>> invalidJson(HttpMessageNotReadableException e){
        if(e.getMessage().contains("java.time.LocalDate")){
            ValidationErrorDTO error = new ValidationErrorDTO("error", "Formato de fecha inválido. Formato correcto: yyyy-MM-dd");
            return ResponseEntity.badRequest().body(List.of(error));
        }
        ValidationErrorDTO error = new ValidationErrorDTO("error", "Formato de datos inválido");
        return ResponseEntity.badRequest().body(List.of(error));
    }
}
