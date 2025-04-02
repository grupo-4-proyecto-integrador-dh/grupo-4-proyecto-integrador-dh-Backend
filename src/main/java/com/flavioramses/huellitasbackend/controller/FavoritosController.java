package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.service.AlojamientoFavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de alojamientos favoritos de los clientes.
 */
@RestController
@RequestMapping("/favoritos")
@CrossOrigin(origins = "*")
@Tag(name = "Favoritos", description = "API para gestionar alojamientos favoritos")
@RequiredArgsConstructor
@Slf4j
public class FavoritosController {
    
    private final AlojamientoFavoritoService favoritoService;
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener favoritos de un cliente", description = "Retorna todos los alojamientos marcados como favoritos por el cliente")
    @ApiResponse(responseCode = "200", description = "Lista de alojamientos favoritos")
    public ResponseEntity<List<AlojamientoDTO>> obtenerFavoritos(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId) {
        log.info("Obteniendo favoritos para el cliente con ID: {}", clienteId);
        return ResponseEntity.ok(favoritoService.obtenerFavoritosPorCliente(clienteId));
    }

    @PostMapping("/cliente/{clienteId}/alojamiento/{alojamientoId}")
    @Operation(summary = "Agregar favorito", description = "Agrega un alojamiento a los favoritos de un cliente")
    @ApiResponse(responseCode = "200", description = "Alojamiento agregado a favoritos")
    @ApiResponse(responseCode = "404", description = "Cliente o alojamiento no encontrado")
    public ResponseEntity<Map<String, Object>> agregarFavorito(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "ID del alojamiento", required = true) @PathVariable Long alojamientoId) {
        try {
            log.info("Agregando alojamiento {} a favoritos del cliente {}", alojamientoId, clienteId);
            boolean agregado = favoritoService.agregarFavorito(clienteId, alojamientoId);
            String mensaje = agregado 
                ? "Alojamiento agregado a favoritos" 
                : "El alojamiento ya estaba en favoritos";
            
            return ResponseEntity.ok(Map.of(
                "agregado", agregado,
                "esFavorito", true,
                "mensaje", mensaje
            ));
        } catch (ResourceNotFoundException e) {
            log.error("Error al agregar favorito: {}", e.getMessage());
            return ResponseEntity.status(404).body(Map.of(
                "error", e.getMessage(),
                "agregado", false,
                "esFavorito", false
            ));
        }
    }

    @DeleteMapping("/cliente/{clienteId}/alojamiento/{alojamientoId}")
    @Operation(summary = "Eliminar favorito", description = "Elimina un alojamiento de los favoritos de un cliente")
    @ApiResponse(responseCode = "200", description = "Alojamiento eliminado de favoritos")
    public ResponseEntity<Map<String, Object>> eliminarFavorito(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "ID del alojamiento", required = true) @PathVariable Long alojamientoId) {
        log.info("Eliminando alojamiento {} de favoritos del cliente {}", alojamientoId, clienteId);
        boolean eliminado = favoritoService.eliminarFavorito(clienteId, alojamientoId);
        String mensaje = eliminado 
            ? "Alojamiento eliminado de favoritos" 
            : "El alojamiento no estaba en favoritos";
        
        return ResponseEntity.ok(Map.of(
            "eliminado", eliminado,
            "esFavorito", false,
            "mensaje", mensaje
        ));
    }


    @GetMapping("/cliente/{clienteId}/alojamiento/{alojamientoId}")
    @Operation(summary = "Verificar favorito", description = "Verifica si un alojamiento es favorito de un cliente")
    @ApiResponse(responseCode = "200", description = "Estado del favorito")
    public ResponseEntity<Map<String, Object>> esFavorito(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "ID del alojamiento", required = true) @PathVariable Long alojamientoId) {
        log.info("Verificando si alojamiento {} es favorito del cliente {}", alojamientoId, clienteId);
        boolean esFavorito = favoritoService.esFavorito(clienteId, alojamientoId);
        
        return ResponseEntity.ok(Map.of(
            "esFavorito", esFavorito
        ));
    }


    @PutMapping("/cliente/{clienteId}/alojamiento/{alojamientoId}/alternar")
    @Operation(summary = "Alternar favorito", description = "Agrega o elimina un alojamiento de favoritos según su estado actual")
    @ApiResponse(responseCode = "200", description = "Estado actualizado")
    @ApiResponse(responseCode = "404", description = "Cliente o alojamiento no encontrado")
    public ResponseEntity<Map<String, Object>> alternarFavorito(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clienteId,
            @Parameter(description = "ID del alojamiento", required = true) @PathVariable Long alojamientoId) {
        try {
            log.info("Alternando estado de favorito del alojamiento {} para el cliente {}", alojamientoId, clienteId);
            boolean esFavorito = favoritoService.alternarFavorito(clienteId, alojamientoId);
            String mensaje = esFavorito 
                ? "Alojamiento agregado a favoritos" 
                : "Alojamiento eliminado de favoritos";
            
            return ResponseEntity.ok(Map.of(
                "esFavorito", esFavorito,
                "mensaje", mensaje
            ));
        } catch (ResourceNotFoundException e) {
            log.error("Error al alternar favorito: {}", e.getMessage());
            return ResponseEntity.status(404).body(Map.of(
                "error", e.getMessage(),
                "esFavorito", false
            ));
        }
    }
}
