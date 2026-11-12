package uy.um.mascotas.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;
import uy.um.mascotas.core.exception.MascotaNoEncontradaException;
import uy.um.mascotas.core.service.MascotaService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MascotaApiController.class)
class MascotaAPIControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean MascotaService service;

    @Test
    void post_conBodyValido_deberiaRetornar201YBody() throws Exception {
        CrearMascotaRequest req = new CrearMascotaRequest("Firulais", Especie.PERRO, Genero.MACHO, 3, null, null);
        MascotaResponse resp = new MascotaResponse(1L, "Firulais", Especie.PERRO, Genero.MACHO, 3, null, null);
        when(service.crear(any())).thenReturn(resp);

        mockMvc.perform(post("/api/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Firulais"));
    }

    @Test
    void post_conBodyInvalido_deberiaRetornar400() throws Exception {
        CrearMascotaRequest req = new CrearMascotaRequest("", null, null, null, null, null);

        mockMvc.perform(post("/api/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_porIdInexistente_deberiaRetornar404() throws Exception {
        when(service.obtener(99L)).thenThrow(new MascotaNoEncontradaException(99L));

        mockMvc.perform(get("/api/mascotas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void get_listar_conFiltros_deberiaInvocarService() throws Exception {
        when(service.listar(Especie.PERRO, null, null, null))
                .thenReturn(List.of(new MascotaResponse(1L, "A", Especie.PERRO, Genero.MACHO, 2, null, null)));

        mockMvc.perform(get("/api/mascotas").param("especie", "PERRO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}