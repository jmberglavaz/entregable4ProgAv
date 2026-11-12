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
    public String listar(
            @RequestParam(required = false) Especie especie,
            @RequestParam(required = false) Genero genero,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax,
            Model model) {
        
        // Ahora sí le pasamos los 4 filtros al servicio
        model.addAttribute("mascotas", service.listar(especie, genero, edadMin, edadMax));
        
        // Guardamos los valores para que no se borren del formulario al filtrar
        model.addAttribute("especieSel", especie);
        model.addAttribute("generoSel", genero);
        model.addAttribute("edadMinSel", edadMin);
        model.addAttribute("edadMaxSel", edadMax);
        
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