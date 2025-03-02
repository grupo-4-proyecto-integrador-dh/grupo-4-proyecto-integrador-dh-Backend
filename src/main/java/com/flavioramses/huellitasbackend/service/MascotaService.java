package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> getMascotaById(Long id) {
        return mascotaRepository.findById(id);
    }

    public Mascota updateMascota(Long id, Mascota mascotaNueva) {
        Mascota mascota = mascotaRepository.findById(id).orElse(null);

        if(mascota == null || mascotaNueva == null) return null;

        mascota.setNombre(mascotaNueva.getNombre());
        // TODO: actualizar futuros campos

        return mascotaRepository.save(mascota);
    }

    public Mascota saveMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public void deleteMascotaById(Long id) {
        mascotaRepository.deleteById(id);
    }
}
