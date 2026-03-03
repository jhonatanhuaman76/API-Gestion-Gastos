package com.mibanco.apigastos.application.mapper;

import com.mibanco.apigastos.domain.model.Gasto;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;

public interface GastoMapper {
    Gasto toEntity(GastoRequestDTO dto);
    GastoResponseDTO toDTO(Gasto gasto);
}
