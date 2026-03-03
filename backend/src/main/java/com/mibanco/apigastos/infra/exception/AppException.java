package com.mibanco.apigastos.infra.exception;

public class AppException extends RuntimeException {
    String fieldName;
    public AppException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
