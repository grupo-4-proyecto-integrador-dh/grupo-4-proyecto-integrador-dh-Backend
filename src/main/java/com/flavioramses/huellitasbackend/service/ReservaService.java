package com.flavioramses.huellitasbackend.service;

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

    public void saveMascota(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    public void deleteReservaById(Long id) {
        reservaRepository.deleteById(id);
    }
}
