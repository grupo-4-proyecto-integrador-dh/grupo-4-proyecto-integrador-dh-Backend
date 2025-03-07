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
            if (alojamientoDTO.getCategoriaIds() == null) {
                throw new BadRequestException("El ID de la categoría no puede ser nulo.");
            }

            Optional<Categoria> categoriaOptional = categoriaService.getCategoriaById(alojamientoDTO.getCategoriaIds());
            if (!categoriaOptional.isPresent()) {
                throw new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaIds());
            }

            Alojamiento alojamientoGuardado = alojamientoService.crearAlojamiento(alojamientoDTO);
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
    public ResponseEntity<Alojamiento> updateAlojamiento(@PathVariable Long id, @RequestBody AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException, BadRequestException {
        try {
            if (alojamientoDTO.getCategoriaIds() == null) {
                throw new BadRequestException("El ID de la categoría no puede ser nulo.");
            }

            Optional<Categoria> categoriaOptional = categoriaService.getCategoriaById(alojamientoDTO.getCategoriaIds());
            if (!categoriaOptional.isPresent()) {
                throw new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaIds());
            }

            return ResponseEntity.ok(alojamientoService.actualizarAlojamiento(id, alojamientoDTO));
        } catch (ResourceNotFoundException | BadRequestException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlojamientoById(@PathVariable("id") Long id) {
        alojamientoService.deleteAlojamientoById(id);
        return ResponseEntity.status(204).build();
    }
}
