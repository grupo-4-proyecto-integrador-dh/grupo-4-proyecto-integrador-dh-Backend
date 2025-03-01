package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.service.AlojamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/alojamientos")
public class AlojamientoController {

    @Autowired
    public AlojamientoService alojamientoService;

    @PostMapping
    public ResponseEntity<Alojamiento> saveAlojamiento(@RequestBody Alojamiento alojamiento) throws BadRequestException {
        Alojamiento alojamientoGuardado = alojamientoService.saveAlojamiento(alojamiento);
        Optional<Alojamiento> alojamientoById = alojamientoService.getAlojamientoById(alojamiento.getId());
        if(alojamientoById.isPresent()){
            return ResponseEntity.ok(alojamientoGuardado);
        } else {
            throw new BadRequestException("Hubo un error al registrar el alojamiento");
        }
    }

    @GetMapping
    public ResponseEntity<List<Alojamiento>> getAllAlojamientos() {
        return ResponseEntity.status(200).body(alojamientoService.getAllAlojamientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Alojamiento>> getAlojamientoById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Alojamiento> alojamientoBuscado = alojamientoService.getAlojamientoById(id);
        if(alojamientoBuscado.isPresent()){
            return ResponseEntity.ok(alojamientoBuscado);
        }else{
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> updateAlojamiento(@PathVariable Long id, @RequestBody Alojamiento alojamiento) throws BadRequestException {
          try{
              return ResponseEntity.ok(alojamientoService.updateAlojamiento(id, alojamiento));
          }catch (Exception e){
              throw new BadRequestException("Ocurrio un error al actualizar el alojamiento");
          }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlojamientoById(@PathVariable("id") Long id) {
        alojamientoService.deleteAlojamientoById(id);
        return ResponseEntity.status(204).build();
    }
}
