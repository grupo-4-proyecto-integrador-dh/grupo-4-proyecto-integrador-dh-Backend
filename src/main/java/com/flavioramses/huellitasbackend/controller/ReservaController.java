package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.model.Reserva;
import com.flavioramses.huellitasbackend.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
    @PostMapping
    public ResponseEntity<Reserva> saveReserva(@RequestBody Reserva reserva) throws BadRequestException {
        Reserva reservaGuardado = reservaService.saveReserva(reserva);
        Optional<Reserva> reservaById = reservaService.getReservaById(reserva.getId());
        if(reservaById.isPresent()){
            return ResponseEntity.ok(reservaGuardado);
        } else {
            throw new BadRequestException("Hubo un error al registrar el reserva");
        }
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        return ResponseEntity.status(200).body(reservaService.getAllReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reserva>> getReservaById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Reserva> reservaBuscado = reservaService.getReservaById(id);
        if(reservaBuscado.isPresent()){
            return ResponseEntity.ok(reservaBuscado);
        }else{
            throw new ResourceNotFoundException("Reserva no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id,@RequestBody Reserva reserva) throws BadRequestException {
        try{
            return ResponseEntity.ok(reservaService.updateReserva(id,reserva));
        }catch (Exception e){
            throw new BadRequestException("Ocurrio un error al actualizar el reserva");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservaById(@PathVariable("id") Long id) {
        reservaService.deleteReservaById(id);
        return ResponseEntity.status(204).build();
    }
}
