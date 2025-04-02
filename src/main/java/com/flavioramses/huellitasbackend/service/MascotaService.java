package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.Exception.UnauthorizedException;
import com.flavioramses.huellitasbackend.dto.MascotaDTO;
import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.repository.ClienteRepository;
import com.flavioramses.huellitasbackend.repository.MascotaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MascotaService {
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;

    public List<Mascota> getMascotasByClienteId(Long clienteId) {
        log.info("Obteniendo mascotas para el cliente con ID: {}", clienteId);
        return mascotaRepository.findByClienteId(clienteId);
    }

    public Mascota getMascotaByIdAndCliente(Long id, Long clienteId) throws ResourceNotFoundException {
        log.info("Buscando mascota con ID: {} para el cliente: {}", id, clienteId);
        return mascotaRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada o no pertenece al cliente."));
    }

    public Mascota saveMascota(MascotaDTO mascotaDTO) throws ResourceNotFoundException {
        log.info("Guardando nueva mascota para el cliente con ID: {}", mascotaDTO.getClienteId());
        Cliente cliente = clienteRepository.findById(mascotaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado."));

        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setPeso(mascotaDTO.getPeso());
        mascota.setEdad(mascotaDTO.getEdad());
        mascota.setObservaciones(mascotaDTO.getObservaciones());
        mascota.setActivo(true);
        mascota.setCliente(cliente);

        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        log.info("Mascota guardada con ID: {}", mascotaGuardada.getId());
        return mascotaGuardada;
    }

    public Mascota updateMascota(Long id, Long clienteId, MascotaDTO mascotaDTO) throws ResourceNotFoundException {
        log.info("Actualizando mascota con ID: {} para el cliente: {}", id, clienteId);
        Mascota mascota = getMascotaByIdAndCliente(id, clienteId);
        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setEspecie(mascotaDTO.getEspecie());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setPeso(mascotaDTO.getPeso());
        mascota.setEdad(mascotaDTO.getEdad());
        mascota.setObservaciones(mascotaDTO.getObservaciones());
        
        Mascota mascotaActualizada = mascotaRepository.save(mascota);
        log.info("Mascota actualizada: {}", mascotaActualizada.getId());
        return mascotaActualizada;
    }

    public void deleteMascotaById(Long id, Long clienteId) throws ResourceNotFoundException {
        log.info("Eliminando mascota con ID: {} para el cliente: {}", id, clienteId);
        Mascota mascota = getMascotaByIdAndCliente(id, clienteId);
        mascotaRepository.delete(mascota);
        log.info("Mascota eliminada");
    }

    @Transactional
    public Mascota cambiarEstadoMascota(Long id, Long clienteId, boolean activo) throws ResourceNotFoundException {
        log.info("Cambiando estado de mascota con ID: {} a activo: {}", id, activo);
        Mascota mascota = getMascotaByIdAndCliente(id, clienteId);
        mascota.setActivo(activo);
        Mascota mascotaActualizada = mascotaRepository.save(mascota);
        log.info("Estado de mascota actualizado");
        return mascotaActualizada;
    }

    public List<Mascota> getAllMascotas() {
        log.info("Obteniendo todas las mascotas");
        return mascotaRepository.findAll();
    }
}


