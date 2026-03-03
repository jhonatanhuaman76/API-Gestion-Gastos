package com.mibanco.apigastos.infra.web.controller;

import com.mibanco.apigastos.application.service.GastoService;
import com.mibanco.apigastos.infra.web.dto.GastoRequestDTO;
import com.mibanco.apigastos.infra.web.dto.GastoResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.mibanco.apigastos.util.JsonUtil.readJson;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GastoController.class)
@DisplayName("Pruebas de GastoController")
class GastoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GastoService gastoService;

    private GastoResponseDTO gastoResponseUpdateDTO;
    private GastoResponseDTO gastoResponseDTO;
    private List<GastoResponseDTO> gastosResponseDTO;
    private List<GastoResponseDTO> gastosAnioMesResponseDTO;

    @BeforeEach
    void setUp() {
        gastoResponseUpdateDTO = new GastoResponseDTO(
                "40e4d24c-365f-4dae-a2ab-89963170c2e4",
                "Cine",
                "Salida al cine con familia",
                LocalDate.of(2025, 9, 10),
                BigDecimal.valueOf(250.00)
        );
        gastoResponseDTO = new GastoResponseDTO(
                "40e4d24c-365f-4dae-a2ab-89963170c2e4",
                "Cine",
                "Salida al cine con amigos",
                LocalDate.of(2025, 9, 15),
                BigDecimal.valueOf(200.00)
        );
        gastosResponseDTO = List.of(
                gastoResponseDTO,
                new GastoResponseDTO(
                        "f9fa7834-664d-4c4d-b95f-c45f5219c1c9",
                        "Almuerzo",
                        "Reunión de trabajo",
                        LocalDate.of(2026, 1, 6),
                        BigDecimal.valueOf(45.50)
                ),
                new GastoResponseDTO(
                        "00125db5-67bc-4959-9704-b462c4e3f38f",
                        "Viaje",
                        "Viaje a Machu Picchu",
                        LocalDate.of(2025, 9, 20),
                        BigDecimal.valueOf(3000.00)
                )
        );
        gastosAnioMesResponseDTO = List.of(
                gastoResponseDTO,
                new GastoResponseDTO(
                        "00125db5-67bc-4959-9704-b462c4e3f38f",
                        "Viaje",
                        "Viaje a Machu Picchu",
                        LocalDate.of(2025, 9, 20),
                        BigDecimal.valueOf(3000.00)
                )
        );
    }

    @Nested
    @DisplayName("Solicitudes GET")
    class SolicitudesGet {
        @Test
        @DisplayName("GET /mibancoapi/gastos debe retornar 200 + lista de gastos")
        void listarGastos() {
            try {
                String expectedResponseJson = readJson("json/gasto/response-listar-gastos.json");
                when(gastoService.listar()).thenReturn(gastosResponseDTO);

                mockMvc.perform(get("/mibancoapi/gastos"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.length()", is(3)))
                        .andExpect(content().json(expectedResponseJson));

                verify(gastoService).listar();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("GET /mibancoapi/gastos/mes-anio debe retornar 200 + lista de gastos")
        void listarGastosPorMesAnio() {
            try {
                String expectedResponseJson = readJson("json/gasto/response-listar-mes-anio-gastos.json");
                when(gastoService.listarPorMesAnio(any(), any())).thenReturn(gastosAnioMesResponseDTO);

                mockMvc.perform(get("/mibancoapi/gastos/mes-anio?mes=9&anio=2025"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.length()", is(2)))
                        .andExpect(content().json(expectedResponseJson));

                verify(gastoService).listarPorMesAnio(any(), any());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @DisplayName("Solicitudes POST")
    class SolicitudesPost {
        @Test
        @DisplayName("POST /mibancoapi/gastos debe retornar 201 + gasto creado")
        void crearGasto() {
            try {
                String requestJson = readJson("json/gasto/request-crear-gasto.json");
                String expectedResponseJson = readJson("json/gasto/response-crear-gasto.json");
                when(gastoService.agregar(any())).thenReturn(gastoResponseDTO);

                mockMvc.perform(post("/mibancoapi/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expectedResponseJson));

                verify(gastoService).agregar(any());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @DisplayName("Solicitudes PUT")
    class SolicitudesPut {
        @Test
        @DisplayName("PUT /mibancoapi/gastos/{id} debe retornar 200 + gasto actualizado")
        void actualizarGasto() {
            try {
                String requestJson = readJson("json/gasto/request-actualizar-gasto.json");
                String expectedResponseJson = readJson("json/gasto/response-actualizar-gasto.json");
                when(gastoService.actualizar(any(), any())).thenReturn(gastoResponseUpdateDTO);

                mockMvc.perform(put("/mibancoapi/gastos/40e4d24c-365f-4dae-a2ab-89963170c2e4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expectedResponseJson));

                verify(gastoService).actualizar(any(), any());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @DisplayName("Solicitudes DELETE")
    class SolicitudesDelete {
        @Test
        @DisplayName("DELETE /mibancoapi/gastos/{id} debe retornar 204")
        void eliminarGasto() {
            try {
                mockMvc.perform(delete("/mibancoapi/gastos/40e4d24c-365f-4dae-a2ab-89963170c2e4"))
                        .andExpect(status().isNoContent());

                verify(gastoService).eliminar(any());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}