package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.AlojamientoDashboardDTO;
import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.Categoria;
import com.flavioramses.huellitasbackend.service.AlojamientoService;
import com.flavioramses.huellitasbackend.service.CategoriaService; // Agrega CategoriaService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/alojamientos")
public class AlojamientoController {

    @Autowired
    public AlojamientoService alojamientoService;

    @Autowired
    public CategoriaService categoriaService; // Agrega CategoriaService

    @PostMapping
    public ResponseEntity<Alojamiento> saveAlojamiento(@RequestBody AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException, BadRequestException {
        try {
            // Validación de la categoría
            if (alojamientoDTO.getCategoriaId() == null) {
                throw new BadRequestException("El ID de la categoría no puede ser nulo.");
            }

            Optional<Categoria> categoriaOptional = categoriaService.getCategoriaById(alojamientoDTO.getCategoriaId());
            if (!categoriaOptional.isPresent()) {
                throw new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaId());
            }

            Alojamiento alojamientoGuardado = alojamientoService.crearAlojamiento(alojamientoDTO);
            return ResponseEntity.ok(alojamientoGuardado);
        } catch (ResourceNotFoundException | BadRequestException e) {
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Alojamiento>> getAllAlojamientos() {
        return ResponseEntity.ok(alojamientoService.getAllAlojamientos());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<AlojamientoDashboardDTO>> getAllAlojamientosForDashboard() {
        List<AlojamientoDashboardDTO> alojamientos = alojamientoService.getAlojamientosDashboardDTO();
        return ResponseEntity.ok(alojamientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alojamiento> getAlojamientoById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Alojamiento alojamientoBuscado = alojamientoService.getAlojamientoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));
        return ResponseEntity.ok(alojamientoBuscado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> updateAlojamiento(@PathVariable Long id, @RequestBody AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException, BadRequestException {
        try {
            if (alojamientoDTO.getCategoriaId() == null) {
                throw new BadRequestException("El ID de la categoría no puede ser nulo.");
            }

            Optional<Categoria> categoriaOptional = categoriaService.getCategoriaById(alojamientoDTO.getCategoriaId());
            if (!categoriaOptional.isPresent()) {
                throw new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaId());
            }

            return ResponseEntity.ok(alojamientoService.actualizarAlojamiento(id, alojamientoDTO));
        } catch (ResourceNotFoundException | BadRequestException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlojamientoById(@PathVariable("id") Long id) {
        alojamientoService.eliminarAlojamientoPorId(id);
        return ResponseEntity.noContent().build();
    }
}