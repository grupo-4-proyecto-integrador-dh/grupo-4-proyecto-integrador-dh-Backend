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

    @GetMapping("/{Id}")
    public Optional<Mascota> getUserById(@PathVariable("Id") Long mascotaId) {
        return mascotaService.getMascotaById(mascotaId);
    }

    @PostMapping
    public void saveUser(@RequestBody Mascota mascota) {
        mascotaService.saveMascota(mascota);
    }

    @DeleteMapping("/{Id}")
    public void deleteMascotaById(@PathVariable("Id") Long mascotaId) {
        mascotaService.deleteMascotaById(mascotaId);
    }

}
