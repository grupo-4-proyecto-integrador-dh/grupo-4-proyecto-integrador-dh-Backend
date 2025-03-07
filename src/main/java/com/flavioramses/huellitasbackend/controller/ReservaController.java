package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.ClienteDTO;
import com.flavioramses.huellitasbackend.dto.MascotaDTO;
import com.flavioramses.huellitasbackend.dto.ReservaDTO;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.Reserva;
import com.flavioramses.huellitasbackend.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> saveReserva(@RequestBody Reserva reserva) throws BadRequestException {
        Reserva reservaGuardado = reservaService.saveReserva(reserva);
        return ResponseEntity.ok(ReservaDTO.toReservaDTO(reservaGuardado));
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        List<ReservaDTO> reservaDTOs = ReservaDTO.toReservaDTOList(reservaService.getAllReservas());
        return ResponseEntity.ok(reservaDTOs);
    }

    @GetMapping("/{id}/alojamiento")
    public ResponseEntity<Alojamiento> getAlojamientoAsociado(@PathVariable Long id) throws ResourceNotFoundException {
        Reserva reserva = reservaService.getReservaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una reserva con el id " + id));
        return ResponseEntity.ok(reserva.getAlojamiento());
    }

    @GetMapping("/{id}/cliente")
    public ResponseEntity<ClienteDTO> getClienteAsociado(@PathVariable Long id) throws ResourceNotFoundException {
        Reserva reserva = reservaService.getReservaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una reserva con el id " + id));
        return ResponseEntity.ok(ClienteDTO.toClienteDTO(reserva.getCliente()));
    }

    @GetMapping("/{id}/mascota")
    public ResponseEntity<MascotaDTO> getMascotaAsociada(@PathVariable Long id) throws ResourceNotFoundException {
        Reserva reserva = reservaService.getReservaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una reserva con el id " + id));
        return ResponseEntity.ok(MascotaDTO.toMascotaDTO(reserva.getMascota()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Reserva reservaBuscada = reservaService.getReservaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return ResponseEntity.ok(ReservaDTO.toReservaDTO(reservaBuscada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(ReservaDTO.toReservaDTO(reservaService.updateReserva(id, reserva)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservaById(@PathVariable("id") Long id) {
        reservaService.deleteReservaById(id);
        return ResponseEntity.noContent().build();
    }
}