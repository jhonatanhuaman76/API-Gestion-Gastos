package com.mibanco.apigastos.infra.repository;

import com.mibanco.apigastos.domain.model.Gasto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de JsonGastoRepository")
class JsonGastoRepositoryTest {

    @TempDir
    Path tempDir;

    private JsonGastoRepository jsonGastoRepository;
    private Gasto gasto1;
    private Gasto gasto2;

    @BeforeEach
    void setUp() {
        Path file = tempDir.resolve("gastos.json");
        ObjectMapper mapper = new ObjectMapper();

        jsonGastoRepository = new JsonGastoRepository(file, mapper);
        gasto1 = new Gasto(
                null,
                "Cine",
                "Salida al cine con amigos",
                LocalDate.of(2025, 9, 15),
                BigDecimal.valueOf(200.00)
        );
        gasto2 = new Gasto(
                null,
                "Almuerzo",
                "Reunión de trabajo",
                LocalDate.of(2026, 1, 6),
                BigDecimal.valueOf(45.50)
        );
    }

    @Test
    @DisplayName("Agregar gasto")
    void agregarGasto() {
        try {
            // ACt
            Gasto gastoGuardado = jsonGastoRepository.agregar(gasto1);

            // Assert
            assertNotNull(gastoGuardado, "El gasto debería existir");
            assertEquals(gastoGuardado.getTitulo(), gasto1.getTitulo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Listar gastos")
    void listarGastos() {
        try {
            // Act
            Gasto gastoGuardado1 = jsonGastoRepository.agregar(gasto1);
            Gasto gastoGuardado2 = jsonGastoRepository.agregar(gasto2);
            List<Gasto> gastos = jsonGastoRepository.listar();

            // Assert
            assertEquals(2, gastos.size());
            assertTrue(gastos.get(0).getId().equals(gastoGuardado1.getId()));
            assertTrue(gastos.get(1).getId().equals(gastoGuardado2.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Listar gastos por mes y año")
    void listarGastosPorMesAnio() {
        try {
            // Act
            Gasto gastoGuardado1 = jsonGastoRepository.agregar(gasto1);
            Gasto gastoGuardado2 = jsonGastoRepository.agregar(gasto2);
            List<Gasto> gastos = jsonGastoRepository.listarPorMesAnio(9, 2025);

            // Assert
            assertEquals(1, gastos.size());
            assertTrue(gastos.get(0).getId().equals(gastoGuardado1.getId()));
            assertFalse(gastos.get(0).getId().equals(gastoGuardado2.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Actualizar gasto")
    void actualizarGasto() {
        try {
            // Act
            Gasto entrada = new Gasto(
                    "40e4d24c-365f-4dae-a2ab-89963170c2e4",
                    "Cine",
                    "Salida al cine con familia",
                    LocalDate.of(2025, 9, 10),
                    BigDecimal.valueOf(250.00)
            );
            Gasto gastoGuardado = jsonGastoRepository.agregar(gasto1);
            Gasto gastoActualizado = jsonGastoRepository.actualizar(entrada);

            // Assert
            assertNotNull(gastoActualizado, "El gasto debería existir");
            assertEquals(gastoActualizado.getTitulo(), entrada.getTitulo());
            assertEquals(gastoActualizado.getFecha(), entrada.getFecha());
            assertEquals(gastoActualizado.getMonto(), entrada.getMonto());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Eliminar gasto")
    void eliminarGasto() {
        try {
            // Act
            jsonGastoRepository.agregar(gasto1);
            jsonGastoRepository.eliminar(gasto1.getId());

            Gasto encontrado = jsonGastoRepository.buscarPorId(gasto1.getId());

            // Assert
            assertNull(encontrado, "El gasto no debería existir");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Buscar gasto por ID")
    void buscarGastoPorId() {
        try {
            // Act
            Gasto gastoGuardado = jsonGastoRepository.agregar(gasto1);
            Gasto encontrado = jsonGastoRepository.buscarPorId(gastoGuardado.getId());

            // Assert
            assertNotNull(encontrado, "El gasto debería existir");
            assertEquals(gastoGuardado.getId(), encontrado.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Buscar gasto por ID que no existe")
    void buscarGastoPorIdQueNoExiste() {
        try {
            // Act
            Gasto encontrado = jsonGastoRepository.buscarPorId("123");

            // Assert
            assertNull(encontrado, "El gasto no debería existir");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}