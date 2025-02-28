package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> getAllMascotas() {
        return mascotaService.getAllMascotas();
    }

    @GetMapping("/{id}")
    public Optional<Mascota> getMascotaById(@PathVariable Long id) {
        return mascotaService.getMascotaById(id);
    }

    @PostMapping
    public void saveMascota(@RequestBody Mascota mascota) {
        mascotaService.saveMascota(mascota);
    }

    @DeleteMapping("/{id}")
    public void deleteMascotaById(@PathVariable Long id) {
        mascotaService.deleteMascotaById(id);
    }

}
