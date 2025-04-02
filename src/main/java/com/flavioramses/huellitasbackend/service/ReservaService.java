package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.Exception.UnauthorizedException;
import com.flavioramses.huellitasbackend.dto.ReservaDTO;
import com.flavioramses.huellitasbackend.dto.ReservaNuevaDTO;
import com.flavioramses.huellitasbackend.model.*;
import com.flavioramses.huellitasbackend.repository.AlojamientoRepository;
import com.flavioramses.huellitasbackend.repository.ClienteRepository;
import com.flavioramses.huellitasbackend.repository.MascotaRepository;
import com.flavioramses.huellitasbackend.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {
    @Autowired
    private final ReservaRepository reservaRepository;
    @Autowired
    private final ClienteRepository clienteRepository;
    @Autowired
    private final AlojamientoRepository alojamientoRepository;
    @Autowired
    private final MascotaRepository mascotaRepository;

    public List<ReservaDTO> getAllReservas() {
        try {
            List<Reserva> reservas = reservaRepository.findAll();
            if (reservas == null || reservas.isEmpty()) {
                return Collections.emptyList();
            }

            return reservas.stream()
                    .map(reserva -> {
                        try {
                            return ReservaDTO.fromEntity(reserva);
                        } catch (Exception e) {
                            System.err.println("Error al procesar reserva ID " + 
                                (reserva != null && reserva.getId() != null ? reserva.getId() : "desconocido") + 
                                ": " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(dto -> dto != null)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error general al obtener las reservas: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las reservas: " + e.getMessage(), e);
        }
    }

    public List<ReservaDTO> getReservasByEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado).stream().map(ReservaDTO::fromEntity).collect(Collectors.toList());
    }
    
    public List<ReservaDTO> getReservasByAlojamiento(Long alojamientoId) throws ResourceNotFoundException {
        if (!alojamientoRepository.existsById(alojamientoId)) {
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
        return reservaRepository.findByAlojamientoId(alojamientoId).stream().map(ReservaDTO::fromEntity).collect(Collectors.toList());
    }
    
    public List<ReservaDTO> getReservasByMascota(Long mascotaId) throws ResourceNotFoundException {
        if (!mascotaRepository.existsById(mascotaId)) {
            throw new ResourceNotFoundException("Mascota no encontrada");
        }
        return reservaRepository.findByMascotaId(mascotaId).stream().map(ReservaDTO::fromEntity).collect(Collectors.toList());
    }
    
    public List<ReservaDTO> getReservasByCliente(Long clienteId) throws ResourceNotFoundException {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        return reservaRepository.findByClienteIdOrderByFechaCreacionDesc(clienteId).stream().map(ReservaDTO::fromEntity).collect(Collectors.toList());
    }

    public ReservaDTO getReservaById(Long id) throws ResourceNotFoundException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return ReservaDTO.fromEntity(reserva);
    }

    @Transactional
    public ReservaDTO saveReserva(ReservaNuevaDTO reservaDTO) throws ResourceNotFoundException, BadRequestException {
        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Mascota mascota = mascotaRepository.findById(reservaDTO.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));

        Alojamiento alojamiento = alojamientoRepository.findById(reservaDTO.getAlojamientoId())
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));

        if (reservaDTO.getFechaDesde().isBefore(LocalDate.now())) {
            throw new BadRequestException("La fecha de inicio debe ser igual o posterior a la fecha actual.");
        }

        if (reservaDTO.getFechaHasta().isBefore(reservaDTO.getFechaDesde())) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }

        boolean estaReservado = reservaRepository.existsByAlojamientoAndFechaEntre(
                alojamiento.getId(), reservaDTO.getFechaDesde(), reservaDTO.getFechaHasta());

        if (estaReservado) {
            throw new BadRequestException("El alojamiento ya está reservado en esas fechas.");
        }

        Reserva reserva = new Reserva(mascota, alojamiento, cliente, reservaDTO.getFechaDesde(), reservaDTO.getFechaHasta());

        return ReservaDTO.fromEntity(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaDTO updateReserva(Long id, ReservaNuevaDTO reservaDTO) throws ResourceNotFoundException, BadRequestException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        if (reserva.getEstado() == EstadoReserva.CANCELADA || reserva.getEstado() == EstadoReserva.COMPLETADA) {
            throw new BadRequestException("No se puede modificar una reserva cancelada o completada");
        }

        if (reservaDTO.getFechaDesde().isBefore(LocalDate.now())) {
            throw new BadRequestException("La fecha de inicio debe ser igual o posterior a la fecha actual.");
        }

        if (reservaDTO.getFechaHasta().isBefore(reservaDTO.getFechaDesde())) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }

        boolean estaReservado = reservaRepository.findReservasActivasEnRango(
                reservaDTO.getFechaDesde(), reservaDTO.getFechaHasta())
                .stream()
                .filter(r -> !r.getId().equals(id) && r.getAlojamiento().getId().equals(reserva.getAlojamiento().getId()))
                .findAny()
                .isPresent();

        if (estaReservado) {
            throw new BadRequestException("El alojamiento ya está reservado en esas fechas.");
        }

        if (reservaDTO.getMascotaId() != null && !reservaDTO.getMascotaId().equals(reserva.getMascota().getId())) {
            Mascota mascota = mascotaRepository.findById(reservaDTO.getMascotaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
            reserva.setMascota(mascota);
        }
        
        if (reservaDTO.getAlojamientoId() != null && !reservaDTO.getAlojamientoId().equals(reserva.getAlojamiento().getId())) {
            Alojamiento alojamiento = alojamientoRepository.findById(reservaDTO.getAlojamientoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));
            reserva.setAlojamiento(alojamiento);
        }

        reserva.setFechaDesde(reservaDTO.getFechaDesde());
        reserva.setFechaHasta(reservaDTO.getFechaHasta());

        return ReservaDTO.fromEntity(reservaRepository.save(reserva));
    }

    @Transactional
    public void deleteReserva(Long id) throws ResourceNotFoundException {
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada");
        }
        reservaRepository.deleteById(id);
    }

    @Transactional
    public ReservaDTO cancelarReserva(Long reservaId, Long clienteId) throws ResourceNotFoundException, UnauthorizedException, BadRequestException {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        if (!reserva.getCliente().getId().equals(clienteId)) {
            throw new UnauthorizedException("No tienes permiso para cancelar esta reserva.");
        }

        if (reserva.getFechaDesde().isBefore(LocalDate.now())) {
            throw new BadRequestException("No se pueden cancelar reservas ya iniciadas o finalizadas.");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return ReservaDTO.fromEntity(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaDTO confirmarReserva(Long reservaId) throws ResourceNotFoundException {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
                
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return ReservaDTO.fromEntity(reservaRepository.save(reserva));
    }
    
    @Transactional
    public ReservaDTO completarReserva(Long reservaId) throws ResourceNotFoundException, BadRequestException {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
                
        if (LocalDate.now().isBefore(reserva.getFechaHasta())) {
            throw new BadRequestException("No se puede completar una reserva antes de su fecha de finalización.");
        }
        
        reserva.setEstado(EstadoReserva.COMPLETADA);
        return ReservaDTO.fromEntity(reservaRepository.save(reserva));
    }
}
