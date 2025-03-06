package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.AlojamientoDashboardDTO;
import com.flavioramses.huellitasbackend.dto.AlojamientoDTO; // Importar AlojamientoDTO
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
    public ResponseEntity<Alojamiento> saveAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO) throws BadRequestException {
        try {
            Alojamiento alojamientoGuardado = alojamientoService.crearAlojamiento(alojamientoDTO);
            return ResponseEntity.ok(alojamientoGuardado);
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("Hubo un error al registrar el alojamiento o la categoría no existe.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Alojamiento>> getAllAlojamientos() {
        return ResponseEntity.status(200).body(alojamientoService.getAllAlojamientos());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<AlojamientoDashboardDTO>> getAllAlojamientosForDashboard() {
        List<AlojamientoDashboardDTO> alojamientos = alojamientoService.obtenerAlojamientosDashboardDTO();
        return ResponseEntity.ok(alojamientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Alojamiento>> getAlojamientoById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Alojamiento> alojamientoBuscado = alojamientoService.obtenerAlojamientoPorId(id); // Corregido el nombre del método
        if (alojamientoBuscado.isPresent()) {
            return ResponseEntity.ok(alojamientoBuscado);
        } else {
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> updateAlojamiento(@PathVariable Long id, @RequestBody AlojamientoDTO alojamientoDTO) throws BadRequestException {
        try {
            return ResponseEntity.ok(alojamientoService.actualizarAlojamiento(id, alojamientoDTO));
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("Ocurrió un error al actualizar el alojamiento o la categoría no existe.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlojamientoById(@PathVariable("id") Long id) {
        alojamientoService.eliminarAlojamientoPorId(id); // Corregido el nombre del método
        return ResponseEntity.status(204).build();
    }
}