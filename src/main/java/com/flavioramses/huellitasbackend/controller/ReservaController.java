package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.Exception.UnauthorizedException;
import com.flavioramses.huellitasbackend.dto.ReservaDTO;
import com.flavioramses.huellitasbackend.dto.ReservaNuevaDTO;
import com.flavioramses.huellitasbackend.model.EstadoReserva;
import com.flavioramses.huellitasbackend.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de reservas de alojamientos.
 */
@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservaController {
    
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<?> getAllReservas() {
        try {
            List<ReservaDTO> reservas = reservaService.getAllReservas();
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas: " + e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getReservasByEstado(@PathVariable EstadoReserva estado) {
        try {
            return ResponseEntity.ok(reservaService.getReservasByEstado(estado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas por estado: " + e.getMessage());
        }
    }

    @GetMapping("/alojamiento/{alojamientoId}")
    public ResponseEntity<?> getReservasByAlojamiento(@PathVariable Long alojamientoId) {
        try {
            return ResponseEntity.ok(reservaService.getReservasByAlojamiento(alojamientoId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas por alojamiento: " + e.getMessage());
        }
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<?> getReservasByMascota(@PathVariable Long mascotaId) {
        try {
            return ResponseEntity.ok(reservaService.getReservasByMascota(mascotaId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas por mascota: " + e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> getReservasByCliente(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(reservaService.getReservasByCliente(clienteId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas por cliente: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservaService.getReservaById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la reserva: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveReserva(@RequestBody ReservaNuevaDTO reservaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.saveReserva(reservaDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la reserva: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @RequestBody ReservaNuevaDTO reservaDTO) {
        try {
            return ResponseEntity.ok(reservaService.updateReserva(id, reservaDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la reserva: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id) {
        try {
            reservaService.deleteReserva(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la reserva: " + e.getMessage());
        }
    }

    @PostMapping("/{reservaId}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long reservaId, @RequestParam Long clienteId) {
        try {
            return ResponseEntity.ok(reservaService.cancelarReserva(reservaId, clienteId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    @PostMapping("/{reservaId}/confirmar")
    public ResponseEntity<?> confirmarReserva(@PathVariable Long reservaId) {
        try {
            return ResponseEntity.ok(reservaService.confirmarReserva(reservaId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al confirmar la reserva: " + e.getMessage());
        }
    }

    @PostMapping("/{reservaId}/completar")
    public ResponseEntity<?> completarReserva(@PathVariable Long reservaId) {
        try {
            return ResponseEntity.ok(reservaService.completarReserva(reservaId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al completar la reserva: " + e.getMessage());
        }
    }
}