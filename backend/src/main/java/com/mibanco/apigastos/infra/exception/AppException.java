package com.mibanco.apigastos.infra.exception;

public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}
