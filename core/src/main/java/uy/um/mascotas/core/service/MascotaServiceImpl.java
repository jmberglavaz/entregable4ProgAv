
package uy.um.mascotas.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.domain.Mascota;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;
import uy.um.mascotas.core.exception.MascotaNoEncontradaException;
import uy.um.mascotas.core.repository.MascotaRepository;

import java.util.List;

@Service
@Transactional
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository repository;

    public MascotaServiceImpl(MascotaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MascotaResponse crear(CrearMascotaRequest request) {
        // Convertimos el DTO recibido en una entidad Mascota.
        Mascota mascota = toEntity(request);

        // Guardamos la mascota en la base de datos.
        Mascota mascotaGuardada = repository.save(mascota);

        // Devolvemos la mascota guardada como DTO de respuesta.
        return toResponse(mascotaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponse> listar(
            Especie especie, Genero genero, Integer edadMin, Integer edadMax) {

        return repository.findAll().stream()

                // Si se indicó una especie, dejamos solo las mascotas de esa especie.
                .filter(m -> especie == null || m.getEspecie() == especie)

                // Si se indicó un género, dejamos solo las mascotas de ese género.
                .filter(m -> genero == null || m.getGenero() == genero)

                // Si se indicó una edad mínima, descartamos edades menores o nulas.
                .filter(m -> edadMin == null ||
                        (m.getEdad() != null && m.getEdad() >= edadMin))

                // Si se indicó una edad máxima, descartamos edades mayores o nulas.
                .filter(m -> edadMax == null ||
                        (m.getEdad() != null && m.getEdad() <= edadMax))

                // Convertimos cada entidad en un DTO de respuesta.
                .map(this::toResponse)

                // Reunimos los resultados en una lista.
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MascotaResponse obtener(Long id) {
        // Buscamos la mascota por su ID.
        // Si no existe, lanzamos nuestra excepción personalizada.
        Mascota mascota = repository.findById(id)
                .orElseThrow(() -> new MascotaNoEncontradaException(id));

        return toResponse(mascota);
    }

    // Convierte el DTO de creación en una entidad para guardar en la base de datos.
    private Mascota toEntity(CrearMascotaRequest request) {
        return new Mascota(
                request.nombre(),
                request.especie(),
                request.genero(),
                request.edad(),
                request.fotoUrl(),
                request.descripcion()
        );
    }

    // Convierte una entidad en el DTO que se devuelve al exterior.
    private MascotaResponse toResponse(Mascota mascota) {
        return new MascotaResponse(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getGenero(),
                mascota.getEdad(),
                mascota.getFotoUrl(),
                mascota.getDescripcion()
        );
    }
}
