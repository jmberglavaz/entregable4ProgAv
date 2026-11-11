package uy.um.mascotas.core.service;

import org.springframework.stereotype.Service;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;
import uy.um.mascotas.core.repository.MascotaRepository;

import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository repository;

    public MascotaServiceImpl(MascotaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MascotaResponse crear(CrearMascotaRequest request) {
        // TODO Persona A: implementar
        throw new UnsupportedOperationException("crear() aún no implementado");
    }

    @Override
    public List<MascotaResponse> listar(Especie especie, Genero genero, Integer edadMin, Integer edadMax) {
        // TODO Persona A: implementar
        throw new UnsupportedOperationException("listar() aún no implementado");
    }

    @Override
    public MascotaResponse obtener(Long id) {
        // TODO Persona A: implementar
        throw new UnsupportedOperationException("obtener() aún no implementado");
    }
}