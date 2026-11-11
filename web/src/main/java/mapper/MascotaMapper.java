package uy.um.mascotas.web.mapper;

import uy.um.mascotas.core.domain.Mascota;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;

public final class MascotaMapper {

    private MascotaMapper() {
    }

    public static Mascota toEntity(CrearMascotaRequest request) {
        // TODO Persona B: implementar
        throw new UnsupportedOperationException("MascotaMapper.toEntity() aún no implementado");
    }

    public static MascotaResponse toResponse(Mascota entity) {
        // TODO Persona B: implementar
        throw new UnsupportedOperationException("MascotaMapper.toResponse() aún no implementado");
    }
}