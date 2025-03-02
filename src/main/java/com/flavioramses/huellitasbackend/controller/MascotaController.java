package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.MascotaDTO;
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
    public List<MascotaDTO> getAllMascotas() {
        return MascotaDTO.toMascotaDTOList(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> getMascotaById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Mascota> mascotaBuscada = mascotaService.getMascotaById(id);
        if(mascotaBuscada.isPresent()){
            return ResponseEntity.ok(
                    MascotaDTO.toMascotaDTO(mascotaBuscada.get())
            );
        }else{
            throw new ResourceNotFoundException("Mascota no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) throws BadRequestException {
        try{
            return ResponseEntity.ok(
                    MascotaDTO.toMascotaDTO(
                            mascotaService.updateMascota(id,mascota)
                    )
            );
        }catch (Exception e){
            throw new BadRequestException("Ocurrio un error al actualizar la mascota");
        }
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> saveMascota(@RequestBody Mascota mascota) throws BadRequestException {
        Mascota mascotaGuardada = mascotaService.saveMascota(mascota);
        Optional<Mascota> mascotaById = mascotaService.getMascotaById(mascota.getId());
        if(mascotaById.isPresent()){
            return ResponseEntity.ok(
                    MascotaDTO.toMascotaDTO(mascotaGuardada)
            );
        } else {
            throw new BadRequestException("Hubo un error al registrar la mascota");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMascotaById(@PathVariable Long id) {
        mascotaService.deleteMascotaById(id);
    }

}
