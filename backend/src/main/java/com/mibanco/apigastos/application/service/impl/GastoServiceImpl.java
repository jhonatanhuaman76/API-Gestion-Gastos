package com.mibanco.apigastos.application.service.impl;

import com.mibanco.apigastos.application.mapper.GastoMapper;
import com.mibanco.apigastos.application.service.GastoService;
import com.mibanco.apigastos.domain.model.Gasto;
import com.mibanco.apigastos.domain.repository.GastoRepository;
import com.mibanco.apigastos.infra.exception.AppException;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoServiceImpl implements GastoService {
    private final GastoMapper gastoMapper;
    private final GastoRepository gastoRepository;

    public GastoServiceImpl(GastoMapper gastoMapper, GastoRepository gastoRepository) {
        this.gastoMapper = gastoMapper;
        this.gastoRepository = gastoRepository;
    }

    @Override
    public GastoResponseDTO agregar(GastoRequestDTO gastoDto) {
        Gasto gasto = gastoMapper.toEntity(gastoDto);
        Gasto gastoGuardado = gastoRepository.agregar(gasto);
        return gastoMapper.toDTO(gastoGuardado);
    }

    @Override
    public GastoResponseDTO actualizar(String id, GastoRequestDTO gastoDto) {
        Gasto gasto = gastoRepository.buscarPorId(id);

        if(gasto == null) throw new AppException("id", "Gasto no encontrado");

        gasto.setTitulo(gastoDto.titulo());
        gasto.setMotivo(gastoDto.motivo());
        gasto.setFecha(gastoDto.fecha());
        gasto.setMonto(gastoDto.monto());

        Gasto gastoGuardado = gastoRepository.actualizar(gasto);
        return gastoMapper.toDTO(gastoGuardado);
    }

    @Override
    public void eliminar(String id) {

        Gasto gasto = gastoRepository.buscarPorId(id);

        if(gasto == null) throw new AppException("id", "Gasto no encontrado");

        gastoRepository.eliminar(id);
    }

    @Override
    public List<GastoResponseDTO> listar() {
        return gastoRepository.listar().stream().map(gastoMapper::toDTO).toList();
    }

    @Override
    public List<GastoResponseDTO> listarPorMesAnio(Integer mes, Integer anio) {

        if(mes < 1 || mes > 12) throw new AppException("mes", "Mes inválido");
        if(anio < 1900 ) throw new AppException("anio", "Año inválido");

        return gastoRepository.listarPorMesAnio(mes, anio)
                .stream().map(gastoMapper::toDTO).toList();
    }
}
