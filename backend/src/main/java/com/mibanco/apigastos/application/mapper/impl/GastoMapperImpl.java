package com.mibanco.apigastos.application.mapper.impl;

import com.mibanco.apigastos.application.mapper.GastoMapper;
import com.mibanco.apigastos.domain.model.Gasto;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class GastoMapperImpl implements GastoMapper {
    @Override
    public Gasto toEntity(GastoRequestDTO dto) {
        return new Gasto(
                null,
                dto.titulo(),
                dto.motivo(),
                dto.fecha(),
                dto.monto()
        );
    }

    @Override
    public GastoResponseDTO toDTO(Gasto gasto) {
        return new GastoResponseDTO(
                gasto.getId(),
                gasto.getTitulo(),
                gasto.getMotivo(),
                gasto.getFecha(),
                gasto.getMonto()
        );
    }
}
