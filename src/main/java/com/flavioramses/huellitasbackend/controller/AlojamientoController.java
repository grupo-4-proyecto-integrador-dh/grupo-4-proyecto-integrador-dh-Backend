package com.flavioramses.huellitasbackend.controller;

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
    public ResponseEntity<Alojamiento> saveAlojamiento(@RequestBody Alojamiento alojamiento) {
        return ResponseEntity.status(200).body(alojamientoService.saveAlojamiento(alojamiento));
    }

    @GetMapping("/listartodos")
    public ResponseEntity<List<Alojamiento>> getAllAlojamientos() {
        return ResponseEntity.status(200).body(alojamientoService.getAllAlojamientos());
    }

    @GetMapping("buscar/{Id}")
    public ResponseEntity<Optional<Alojamiento>> getAlojamientoById(@PathVariable Long alojamientoId) {
        return ResponseEntity.status(200).body(alojamientoService.getAlojamientoById(alojamientoId));
    }

    @PutMapping
    public ResponseEntity<Alojamiento> updateAlojamiento(@RequestBody Alojamiento alojamiento) {
            return ResponseEntity.status(200).body(alojamientoService.updateAlojamiento(alojamiento));
    }

    @DeleteMapping("/eliminar/{Id}")
    public ResponseEntity<Void> deleteAlojamientoById(@PathVariable("Id") Long alojamientoId) {
        alojamientoService.deleteAlojamientoById(alojamientoId);
        return ResponseEntity.status(204).build();
    }
}
