package com.mibanco.apigastos.infra.web.controller;

import com.mibanco.apigastos.application.service.GastoService;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mibancoapi/gastos")
public class GastoController {
    private final GastoService gastoService;

    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @PostMapping
    public ResponseEntity<GastoResponseDTO> agregarGasto(@RequestBody @Valid GastoRequestDTO gastoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gastoService.agregar(gastoRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<GastoResponseDTO>> listarGastos() {
        return ResponseEntity.ok(gastoService.listar());
    }

    @DeleteMapping("{id}")
    public ResponseEntity eliminarGasto(@PathVariable String id) {
        gastoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<GastoResponseDTO> actualizarGasto(@PathVariable String id, @RequestBody @Valid GastoRequestDTO gastoRequestDTO) {
        return ResponseEntity.ok(gastoService.actualizar(id, gastoRequestDTO));
    }

    @GetMapping("/mes-anio")
    public ResponseEntity<List<GastoResponseDTO>> buscarGastosPorMesAnio(@RequestParam Integer mes, @RequestParam Integer anio) {
        return ResponseEntity.ok(gastoService.listarPorMesAnio(mes, anio));
    }
}
