package uy.um.mascotas.core.dto;

import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;

public record MascotaResponse(
        Long id,
        String nombre,
        Especie especie,
        Genero genero,
        Integer edad,
        String fotoUrl,
        String descripcion
) {
}
