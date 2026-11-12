package controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;
import uy.um.mascotas.core.service.MascotaService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaAPIController {

    private final MascotaService service;

    public MascotaAPIController(MascotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@Valid @RequestBody CrearMascotaRequest request) {
        MascotaResponse created = service.crear(request);
        return ResponseEntity.created(URI.create("/api/mascotas/" + created.id())).body(created);
    }

    @GetMapping
    public List<MascotaResponse> listar(
            @RequestParam(required = false) Especie especie,
            @RequestParam(required = false) Genero genero,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax) {
        return service.listar(especie, genero, edadMin, edadMax);
    }

    @GetMapping("/{id}")
    public MascotaResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }
}