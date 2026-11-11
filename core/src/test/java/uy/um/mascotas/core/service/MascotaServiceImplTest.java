package uy.um.mascotas.core.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.domain.Mascota;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;
import uy.um.mascotas.core.exception.MascotaNoEncontradaException;
import uy.um.mascotas.core.repository.MascotaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MascotaServiceImplTest {

    @Mock
    private MascotaRepository repository;

    @InjectMocks
    private MascotaServiceImpl service;

    @Test
    void crear_deberiaPersistirYRetornarResponse() {
        // Datos que recibimos para crear la mascota.
        CrearMascotaRequest request = new CrearMascotaRequest(
                "Luna", Especie.PERRO, Genero.HEMBRA, 3, null, null
        );

        // Simulamos que el repositorio guarda la mascota y le asigna el ID 1.
        when(repository.save(org.mockito.ArgumentMatchers.any(Mascota.class)))
                .thenAnswer(invocation -> {
                    Mascota mascota = invocation.getArgument(0);
                    mascota.setId(1L);
                    return mascota;
                });

        MascotaResponse response = service.crear(request);

        // Comprobamos que se guardó y que la respuesta contiene los datos esperados.
        verify(repository).save(org.mockito.ArgumentMatchers.any(Mascota.class));
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nombre()).isEqualTo("Luna");
        assertThat(response.especie()).isEqualTo(Especie.PERRO);
        assertThat(response.genero()).isEqualTo(Genero.HEMBRA);
        assertThat(response.edad()).isEqualTo(3);
    }

    @Test
    void listar_sinFiltros_deberiaRetornarTodas() {
        // Simulamos que existen dos mascotas en el repositorio.
        when(repository.findAll()).thenReturn(List.of(
                mascota(1L, "Luna", Especie.PERRO, Genero.HEMBRA, 3),
                mascota(2L, "Michi", Especie.GATO, Genero.MACHO, 5)
        ));

        List<MascotaResponse> resultado =
                service.listar(null, null, null, null);

        assertThat(resultado).hasSize(2);
        assertThat(resultado)
                .extracting(MascotaResponse::nombre)
                .containsExactly("Luna", "Michi");
    }

    @Test
    void listar_conEspecieYGenero_deberiaFiltrar() {
        when(repository.findAll()).thenReturn(List.of(
                mascota(1L, "Luna", Especie.PERRO, Genero.HEMBRA, 3),
                mascota(2L, "Toby", Especie.PERRO, Genero.MACHO, 4),
                mascota(3L, "Michi", Especie.GATO, Genero.HEMBRA, 2)
        ));

        List<MascotaResponse> resultado =
                service.listar(Especie.PERRO, Genero.HEMBRA, null, null);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).nombre()).isEqualTo("Luna");
    }

    @Test
    void listar_conEdadMinYMax_deberiaFiltrarPorRango() {
        when(repository.findAll()).thenReturn(List.of(
                mascota(1L, "Luna", Especie.PERRO, Genero.HEMBRA, 1),
                mascota(2L, "Toby", Especie.PERRO, Genero.MACHO, 5),
                mascota(3L, "Michi", Especie.GATO, Genero.HEMBRA, 10)
        ));

        List<MascotaResponse> resultado =
                service.listar(null, null, 3, 7);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).nombre()).isEqualTo("Toby");
    }

    @Test
    void obtener_conIdExistente_deberiaRetornar() {
        Mascota mascota = mascota(
                1L, "Luna", Especie.PERRO, Genero.HEMBRA, 3
        );

        when(repository.findById(1L)).thenReturn(Optional.of(mascota));

        MascotaResponse response = service.obtener(1L);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nombre()).isEqualTo("Luna");
    }

    @Test
    void obtener_conIdInexistente_deberiaLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                MascotaNoEncontradaException.class,
                () -> service.obtener(99L)
        );
    }

    // Método auxiliar para crear mascotas de prueba.
    private Mascota mascota(
            Long id, String nombre, Especie especie, Genero genero, Integer edad) {

        Mascota mascota = new Mascota(
                nombre, especie, genero, edad, null, null
        );

        mascota.setId(id);
        return mascota;
    }
}
