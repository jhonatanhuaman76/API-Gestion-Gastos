package com.mibanco.apigastos.domain.repository;

import com.mibanco.apigastos.domain.model.Gasto;

import java.util.List;

public interface GastoRepository {
    Gasto agregar(Gasto gasto);
    Gasto actualizar(Gasto gasto);
    void eliminar(String id);
    List<Gasto> listar();
    List<Gasto> listarPorMesAnio(Integer mes, Integer anio);
    Gasto buscarPorId(String id);
}
