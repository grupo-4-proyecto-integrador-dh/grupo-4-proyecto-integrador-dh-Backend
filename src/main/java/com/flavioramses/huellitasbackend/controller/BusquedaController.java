package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.service.BusquedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/busqueda")
@CrossOrigin(origins = "*")
public class BusquedaController {

    private final BusquedaService busquedaService;

    @Autowired
    public BusquedaController(BusquedaService busquedaService) {
        this.busquedaService = busquedaService;
    }


    @GetMapping("/disponibles")
    public ResponseEntity<List<AlojamientoDTO>> buscarAlojamientosDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String nombre) {
        
        if (fechaInicio.isAfter(fechaFin)) {
            return ResponseEntity.badRequest().build();
        }
        
        List<AlojamientoDTO> alojamientos = busquedaService.buscarAlojamientosDisponibles(
                fechaInicio, fechaFin, nombre);
        
        return ResponseEntity.ok(alojamientos);
    }
    

    @GetMapping("/disponibilidad/{alojamientoId}")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @PathVariable Long alojamientoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        

        if (fechaInicio.isAfter(fechaFin)) {
            return ResponseEntity.badRequest().build();
        }
        
        boolean disponible = busquedaService.verificarDisponibilidad(
                alojamientoId, fechaInicio, fechaFin);
        
        return ResponseEntity.ok(disponible);
    }
}