package com.mibanco.apigastos.application.service;

import com.mibanco.apigastos.domain.model.Gasto;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;

import java.util.List;

public interface GastoService {
    GastoResponseDTO agregar(GastoRequestDTO gastoDto);
    GastoResponseDTO actualizar(String id, GastoRequestDTO gastoDto);
    void eliminar(String id);
    List<GastoResponseDTO> listar();
    List<GastoResponseDTO> listarPorMesAnio(Integer mes, Integer anio);
}
