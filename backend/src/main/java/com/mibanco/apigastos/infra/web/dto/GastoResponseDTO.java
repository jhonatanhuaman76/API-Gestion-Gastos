package com.mibanco.apigastos.infra.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoResponseDTO(
        String id,
        String titulo,
        String motivo,
        LocalDate fecha,
        BigDecimal monto
) {
}
