package com.mibanco.apigastos.infra.repository;

import com.mibanco.apigastos.domain.model.Gasto;
import com.mibanco.apigastos.domain.repository.GastoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
public class JsonGastoRepository implements GastoRepository {
    private final Path filePath = Paths.get("app/data/gastos.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonGastoRepository(){
        existeArchivo();
    }

    private void existeArchivo(){
       try {
           if(!Files.exists(filePath.getParent()))
               Files.createDirectories(filePath.getParent());

           if(!Files.exists(filePath))
               mapper.writeValue(filePath, List.of());
       } catch (IOException e) {
           throw new RuntimeException("No se pudo crear el archivo de gastos.json",e);
       }
    }

    @Override
    public Gasto agregar(Gasto gasto) {
        List<Gasto> gastos = listar();
        gasto.setId(UUID.randomUUID().toString());
        gastos.add(gasto);
        mapper.writeValue(filePath, gastos);
        return gasto;
    }

    @Override
    public Gasto actualizar(Gasto gasto) {
        List<Gasto> gastos = listar();
        gastos.replaceAll(g -> g.getId().equals(gasto.getId()) ? gasto : g);
        mapper.writeValue(filePath, gastos);
        return gasto;
    }

    @Override
    public void eliminar(String id) {
        List<Gasto> gastos = listar();
        gastos.removeIf(gasto -> gasto.getId().equals(id));
        mapper.writeValue(filePath, gastos);
    }

    @Override
    public List<Gasto> listar() {
        List<Gasto> gastos = mapper.readValue(filePath, new TypeReference<>() {
        });
        return gastos;
    }

    @Override
    public List<Gasto> listarPorMesAnio(Integer month, Integer year) {
        List<Gasto> gastos = listar();
        return gastos.stream()
                .filter(gasto -> gasto.getFecha().getMonthValue() == month && gasto.getFecha().getYear() == year)
                .toList();
    }

    @Override
    public Gasto buscarPorId(String id){
        List<Gasto> gastos = listar();
        return  gastos.stream()
                .filter(gasto -> gasto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
