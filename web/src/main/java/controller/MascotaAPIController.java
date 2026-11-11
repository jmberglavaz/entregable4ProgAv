package uy.um.mascotas.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.dto.MascotaResponse;
import uy.um.mascotas.core.service.MascotaService;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaAPIController {

    private final MascotaService service;

    public MascotaAPIController(MascotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@RequestBody CrearMascotaRequest request) {
        // TODO Persona B: implementar delegando al service
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponse>> listar(
            @RequestParam(required = false) Especie especie,
            @RequestParam(required = false) Genero genero,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax) {
        // TODO Persona B: implementar
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> obtener(@PathVariable Long id) {
        // TODO Persona B: implementar
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}