package com.mibanco.apigastos.infra.web.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRequestDTO(
        @NotEmpty(message = "El titulo del gasto no puede estar vacio")
        @Length(min = 3, max = 100, message = "El titulo debe tener entre 3 y 100 caracteres")
        String titulo,
        @NotEmpty(message = "El motivo del gasto no puede estar vacio")
        @Length(min = 3, max = 255, message = "El motivo debe tener entre 3 y 255 caracteres")
        String motivo,
        @NotNull(message = "La fecha no puede estar vacio")
        LocalDate fecha,
        @NotNull(message = "El monto no puede estar vacio")
        @Min(value = 0, message = "El monto debe ser mayor a 0")
        @Digits(integer = 15, fraction = 2, message = "El monto debe tener entre 15 digitos enteros y 2 decimales")
        BigDecimal monto
) {
}
