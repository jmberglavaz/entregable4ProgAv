package uy.um.mascotas.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;

public record CrearMascotaRequest(
        @NotBlank String nombre,
        @NotNull Especie especie,
        @NotNull Genero genero,
        @Min(0) @Max(30) Integer edad,
        String fotoUrl,
        String descripcion
) {
}
