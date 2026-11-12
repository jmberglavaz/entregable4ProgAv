package uy.um.mascotas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uy.um.mascotas.core.domain.Especie;
import uy.um.mascotas.core.domain.Genero;
import uy.um.mascotas.core.dto.CrearMascotaRequest;
import uy.um.mascotas.core.service.MascotaService;

@Controller
@RequestMapping("/mascotas")
public class MascotaWebController {

    private final MascotaService service;

    public MascotaWebController(MascotaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Especie especie, Model model) {
        // Le pasamos el filtro al service. Si especie es null, trae todas.
        model.addAttribute("mascotas", service.listar(especie, null, null, null));
        return "mascotas/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("especies", Especie.values());
        model.addAttribute("generos", Genero.values());
        return "mascotas/form";
    }

    @PostMapping
    public String crear(@ModelAttribute CrearMascotaRequest request) {
        service.crear(request);
        return "redirect:/mascotas";
    }
}