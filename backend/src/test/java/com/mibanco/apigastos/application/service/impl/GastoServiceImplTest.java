package com.mibanco.apigastos.application.service.impl;

import com.mibanco.apigastos.application.mapper.GastoMapper;
import com.mibanco.apigastos.domain.model.Gasto;
import com.mibanco.apigastos.domain.repository.GastoRepository;
import com.mibanco.apigastos.infra.exception.AppException;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de GastoServiceImpl")
class GastoServiceImplTest {

    @Mock
    private GastoRepository gastoRepository;

    @Mock
    private GastoMapper gastoMapper;

    @InjectMocks
    private GastoServiceImpl gastoService;

    private Gasto gasto1;
    private Gasto gasto2;
    private GastoRequestDTO gastoRequestDTO;
    private GastoResponseDTO gastoResponseDTO;

    @BeforeEach
    void setUp() {
        gasto1 = new Gasto(
                "40e4d24c-365f-4dae-a2ab-89963170c2e4",
                "Cine",
                "Salida al cine con amigos",
                LocalDate.of(2025, 9, 15),
                BigDecimal.valueOf(200.00)
        );
        gasto2 = new Gasto(
                "f9fa7834-664d-4c4d-b95f-c45f5219c1c9",
                "Almuerzo",
                "Reunión de trabajo",
                LocalDate.of(2026, 1, 6),
                BigDecimal.valueOf(45.50)
        );
        gastoRequestDTO = new GastoRequestDTO(
                "Cine",
                "Salida al cine con amigos",
                LocalDate.of(2025, 9, 15),
                BigDecimal.valueOf(200.00)
        );
        gastoResponseDTO = new GastoResponseDTO(
                "40e4d24c-365f-4dae-a2ab-89963170c2e4",
                "Cine",
                "Salida al cine con amigos",
                LocalDate.of(2025, 9, 15),
                BigDecimal.valueOf(200.00)
        );
    }

    @Test
    void agregar() {
        try {
            // Arrange
            when(gastoRepository.agregar(any())).thenReturn(gasto1);
            when(gastoMapper.toEntity(any())).thenReturn(gasto1);
            when(gastoMapper.toDTO(any())).thenReturn(gastoResponseDTO);

            // Act
            GastoResponseDTO result = gastoService.agregar(gastoRequestDTO);

            // Assert
            assertNotNull(result);
            assertEquals(gastoResponseDTO, result);
            verify(gastoRepository).agregar(any());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void agregar_monto_invalido() {
        try {
            // Arrange
            GastoRequestDTO gastoRequestDTO = new GastoRequestDTO(
                    "Cine",
                    "Salida al cine con amigos",
                    LocalDate.of(2025, 9, 15),
                    BigDecimal.valueOf(-100.00)
            );

            // Act & Assert
            assertThrows(AppException.class, () -> gastoService.agregar(gastoRequestDTO), "El monto debe ser mayor a 0");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void listar() {
        try {
            // Arrange
            when(gastoRepository.listar()).thenReturn(List.of(gasto1, gasto2));
            when(gastoMapper.toDTO(any())).thenReturn(gastoResponseDTO);

            // Act
            List<GastoResponseDTO> result = gastoService.listar();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(gastoResponseDTO, result.get(0));
            assertEquals(gastoResponseDTO, result.get(1));
            verify(gastoRepository).listar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void listarPorMesAnio() {
        try {
            // Arrange
            when(gastoRepository.listarPorMesAnio(any(), any())).thenReturn(List.of(gasto1, gasto2));
            when(gastoMapper.toDTO(any())).thenReturn(gastoResponseDTO);

            // Act
            List<GastoResponseDTO> result = gastoService.listarPorMesAnio(9, 2025);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(gastoResponseDTO, result.get(0));
            assertEquals(gastoResponseDTO, result.get(1));
            verify(gastoRepository).listarPorMesAnio(any(), any());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void actualizar() {
        try {
            // Arrange
            when(gastoRepository.actualizar(any())).thenReturn(gasto1);
            when(gastoRepository.buscarPorId(any())).thenReturn(gasto1);
            when(gastoMapper.toDTO(any())).thenReturn(gastoResponseDTO);

            // Act
            GastoResponseDTO result = gastoService.actualizar("40e4d24c-365f-4dae-a2ab-89963170c2e4", gastoRequestDTO);

            // Assert
            assertNotNull(result);
            assertEquals(gastoResponseDTO, result);
            verify(gastoRepository).actualizar(any());
            verify(gastoRepository).buscarPorId(any());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void actualizar_monto_invalido() {
        try {
            // Arrange
            GastoRequestDTO gastoRequestDTO = new GastoRequestDTO(
                    "Cine",
                    "Salida al cine con amigos",
                    LocalDate.of(2025, 9, 15),
                    BigDecimal.valueOf(-100.00)
            );

            // Act & Assert
            assertThrows(AppException.class, () -> gastoService.actualizar("40e4d24c-365f-4dae-a2ab-89963170c2e4", gastoRequestDTO), "El monto debe ser mayor a 0");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void actualizar_id_inexistente() {
        // Arrange
        when(gastoRepository.buscarPorId(any())).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class,()->
            gastoService.actualizar("40e4d24c-365f-4dae-a2ab-89963170c2e4", gastoRequestDTO),
            "El gasto no existe"
        );
    }

    @Test
    void eliminar() {
        try {
            // Arrange
            when(gastoRepository.buscarPorId(any())).thenReturn(gasto1);

            // Act
            gastoService.eliminar("40e4d24c-365f-4dae-a2ab-89963170c2e4");

            // Assert
            verify(gastoRepository).eliminar(any());
            verify(gastoRepository).buscarPorId(any());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void eliminar_id_inexistente() {
        try {
            // Arrange
            when(gastoRepository.buscarPorId(any())).thenReturn(null);

            // Act & Assert
            assertThrows(RuntimeException.class,()->
                gastoService.eliminar("40e4d24c-365f-4dae-a2ab-89963170c2e4"),
                "El gasto no existe"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}