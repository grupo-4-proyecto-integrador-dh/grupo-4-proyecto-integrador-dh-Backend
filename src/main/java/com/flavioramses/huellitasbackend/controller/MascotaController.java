package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.MascotaDTO;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "API para gestionar mascotas de clientes")
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener mascotas por cliente", description = "Retorna todas las mascotas asociadas a un cliente")
    public ResponseEntity<List<MascotaDTO>> getMascotasByClienteId(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId) {
        List<Mascota> mascotas = mascotaService.getMascotasByClienteId(clienteId);
        return ResponseEntity.ok(MascotaDTO.toMascotaDTOList(mascotas));
    }
    
    @GetMapping
    @Operation(summary = "Obtener todas las mascotas", description = "Retorna todas las mascotas registradas")
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        return ResponseEntity.ok(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}/cliente/{clienteId}")
    @Operation(summary = "Obtener mascota por ID y cliente", description = "Retorna una mascota específica si pertenece al cliente indicado")
    public ResponseEntity<MascotaDTO> getMascotaById(
            @Parameter(description = "ID de la mascota", required = true) @PathVariable Long id, 
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId) throws ResourceNotFoundException {
        Mascota mascota = mascotaService.getMascotaByIdAndCliente(id, clienteId);
        return ResponseEntity.ok(MascotaDTO.toMascotaDTO(mascota));
    }

    @PostMapping
    @Operation(summary = "Crear mascota", description = "Crea una nueva mascota asociada a un cliente")
    public ResponseEntity<MascotaDTO> saveMascota(
            @Parameter(description = "Datos de la mascota", required = true) @RequestBody MascotaDTO mascotaDTO) throws ResourceNotFoundException {
        Mascota nuevaMascota = mascotaService.saveMascota(mascotaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MascotaDTO.toMascotaDTO(nuevaMascota));
    }

    @PutMapping("/{id}/cliente/{clienteId}")
    @Operation(summary = "Actualizar mascota", description = "Actualiza los datos de una mascota existente")
    public ResponseEntity<MascotaDTO> updateMascota(
            @Parameter(description = "ID de la mascota", required = true) @PathVariable Long id,
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "Datos actualizados de la mascota", required = true) @RequestBody MascotaDTO mascotaDTO) throws ResourceNotFoundException {
        Mascota mascotaActualizada = mascotaService.updateMascota(id, clienteId, mascotaDTO);
        return ResponseEntity.ok(MascotaDTO.toMascotaDTO(mascotaActualizada));
    }

    @DeleteMapping("/{id}/cliente/{clienteId}")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota si pertenece al cliente indicado")
    public ResponseEntity<Void> deleteMascotaById(
            @Parameter(description = "ID de la mascota", required = true) @PathVariable Long id, 
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId) throws ResourceNotFoundException {
        mascotaService.deleteMascotaById(id, clienteId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cliente/{clienteId}/estado")
    @Operation(summary = "Cambiar estado de mascota", description = "Activa o desactiva una mascota")
    public ResponseEntity<MascotaDTO> cambiarEstadoMascota(
            @Parameter(description = "ID de la mascota", required = true) @PathVariable Long id,
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "Estado activo (true) o inactivo (false)", required = true) @RequestParam boolean activo) throws ResourceNotFoundException {
        Mascota mascota = mascotaService.cambiarEstadoMascota(id, clienteId, activo);
        return ResponseEntity.ok(MascotaDTO.toMascotaDTO(mascota));
    }
}
