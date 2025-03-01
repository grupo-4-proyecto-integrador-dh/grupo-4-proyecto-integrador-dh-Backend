package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) throws BadRequestException {
        try{
            return ResponseEntity.ok(mascotaService.updateMascota(id,mascota));
        }catch (Exception e){
            throw new BadRequestException("Ocurrio un error al actualizar la mascota");
        }
    }

    @PostMapping
    public ResponseEntity<Mascota> saveMascota(@RequestBody Mascota mascota) throws BadRequestException {
        Mascota mascotaGuardado = mascotaService.saveMascota(mascota);
        Optional<Mascota> mascotaById = mascotaService.getMascotaById(mascota.getId());
        if(mascotaById.isPresent()){
            return ResponseEntity.ok(mascotaGuardado);
        } else {
            throw new BadRequestException("Hubo un error al registrar la mascota");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMascotaById(@PathVariable Long id) {
        mascotaService.deleteMascotaById(id);
    }

}
