package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.dto.ReservaNuevaDTO;
import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.model.Reserva;
import com.flavioramses.huellitasbackend.repository.AlojamientoRepository;
import com.flavioramses.huellitasbackend.repository.ClienteRepository;
import com.flavioramses.huellitasbackend.repository.MascotaRepository;
import com.flavioramses.huellitasbackend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flavioramses.huellitasbackend.model.Alojamiento;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class ReservaService {


    private final ReservaRepository reservaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AlojamientoRepository alojamientoRepository;

    @Autowired
    MascotaRepository mascotaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public List<Reserva> buscarReservasPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.findByFechaDesdeBetween(fechaInicio, fechaFin);
    }

    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva updateReserva(Long id, Reserva reservaNueva) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if(reserva == null || reservaNueva == null) return null;

        reserva.setFechaDesde(reservaNueva.getFechaDesde());
        reserva.setFechaHasta(reservaNueva.getFechaHasta());
        // TODO: actualizar campos basado en la lógica, por ahora solo la fecha

        return reservaRepository.save(reserva);
    }

    public Reserva saveReserva(ReservaNuevaDTO reservaDTO) {
        if (reservaDTO == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        // Buscar cliente en la base de datos
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // Buscar alojamiento en la base de datos
        Alojamiento alojamiento = alojamientoRepository.findById(reservaDTO.getAlojamientoId())
                .orElseThrow(() -> new IllegalArgumentException("Alojamiento no encontrado"));

        // Buscar mascota en la base de datos
        Mascota mascota = mascotaRepository.findById(reservaDTO.getMascotaId())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setAlojamiento(alojamiento);
        reserva.setMascota(mascota);
        reserva.setFechaDesde(reservaDTO.getFechaDesde());
        reserva.setFechaHasta(reservaDTO.getFechaHasta());

        return reservaRepository.save(reserva);
    }


    public void deleteReservaById(Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Alojamiento> buscarAlojamientosDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.findAlojamientosDisponibles(fechaInicio, fechaFin);
    }

}



