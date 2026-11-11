package uy.um.mascotas.core.service;

import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;

import java.util.List;

public interface MascotaService {

    MascotaResponse crear(CrearMascotaRequest request);

    List<MascotaResponse> listar(Especie especie, Genero genero, Integer edadMin, Integer edadMax);

    MascotaResponse obtener(Long id);
}