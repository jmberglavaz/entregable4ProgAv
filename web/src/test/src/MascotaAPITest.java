package uy.um.mascotas.web.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MascotaAPIIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void flujoCrearYListar_deberiaFuncionarContraH2() throws Exception {
        CrearMascotaRequest req = new CrearMascotaRequest("Firulais", Especie.PERRO, Genero.MACHO, 3, null, "Amistoso");

        mockMvc.perform(post("/api/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Firulais"));

        mockMvc.perform(get("/api/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.nombre == 'Firulais')]").exists());
    }

    @Test
    void flujoFiltroPorEspecie_deberiaRetornarSoloPerros() throws Exception {
        CrearMascotaRequest perro = new CrearMascotaRequest("Rex", Especie.PERRO, Genero.MACHO, 2, null, null);
        CrearMascotaRequest gato = new CrearMascotaRequest("Michi", Especie.GATO, Genero.HEMBRA, 1, null, null);
        mockMvc.perform(post("/api/mascotas").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(perro)));
        mockMvc.perform(post("/api/mascotas").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(gato)));

        mockMvc.perform(get("/api/mascotas").param("especie", "PERRO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].especie").value("PERRO"));
    }
}