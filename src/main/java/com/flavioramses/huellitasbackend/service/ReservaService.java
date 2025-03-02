package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.model.Reserva;
import com.flavioramses.huellitasbackend.model.Reserva;
import com.flavioramses.huellitasbackend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {


    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva updateReserva(Long id, Reserva reservaNueva) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);

        if(reserva == null || reservaNueva == null) return null;

        reserva.setFechaDesde(reservaNueva.getFechaDesde());
        reserva.setFechaHasta(reservaNueva.getFechaHasta());
        reserva.setHoraDesde(reservaNueva.getHoraDesde());
        reserva.setHoraHasta(reservaNueva.getHoraHasta());
        // TODO: actualizar campos basado en la lógica, por ahora solo la fecha y hora

        return reservaRepository.save(reserva);
    }
    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void deleteReservaById(Long id) {
        reservaRepository.deleteById(id);
    }
}
