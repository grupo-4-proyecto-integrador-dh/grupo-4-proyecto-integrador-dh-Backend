package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.dto.ReservaDTO;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.AlojamientoFavorito;
import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.ImagenAlojamiento;
import com.flavioramses.huellitasbackend.repository.AlojamientoFavoritoRepository;
import com.flavioramses.huellitasbackend.repository.AlojamientoRepository;
import com.flavioramses.huellitasbackend.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlojamientoFavoritoService {
    
    private final AlojamientoFavoritoRepository favoritoRepository;
    private final ClienteRepository clienteRepository;
    private final AlojamientoRepository alojamientoRepository;

    public List<AlojamientoDTO> obtenerFavoritosPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            log.warn("Cliente con ID {} no encontrado al obtener favoritos", clienteId);
            return List.of();
        }

        return favoritoRepository.findByClienteIdWithAlojamiento(clienteId).stream()
                .map(favorito -> mapToDTO(favorito.getAlojamiento(), true))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean agregarFavorito(Long clienteId, Long alojamientoId) throws ResourceNotFoundException {
        if (favoritoRepository.existsByClienteIdAndAlojamientoId(clienteId, alojamientoId)) {
            log.info("El alojamiento {} ya es favorito del cliente {}", alojamientoId, clienteId);
            return false;
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    log.error("Cliente con ID {} no encontrado al agregar favorito", clienteId);
                    return new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId);
                });

        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> {
                    log.error("Alojamiento con ID {} no encontrado al agregar favorito", alojamientoId);
                    return new ResourceNotFoundException("Alojamiento no encontrado con ID: " + alojamientoId);
                });

        AlojamientoFavorito favorito = AlojamientoFavorito.crear(cliente, alojamiento);
        favoritoRepository.save(favorito);
        
        log.info("Alojamiento {} agregado como favorito para el cliente {}", alojamientoId, clienteId);
        return true;
    }

    @Transactional
    public boolean eliminarFavorito(Long clienteId, Long alojamientoId) {
        if (!favoritoRepository.existsByClienteIdAndAlojamientoId(clienteId, alojamientoId)) {
            log.info("El alojamiento {} no es favorito del cliente {}", alojamientoId, clienteId);
            return false;
        }

        favoritoRepository.deleteByClienteIdAndAlojamientoId(clienteId, alojamientoId);
        
        log.info("Alojamiento {} eliminado de favoritos para el cliente {}", alojamientoId, clienteId);
        return true;
    }

    public boolean esFavorito(Long clienteId, Long alojamientoId) {
        return favoritoRepository.esFavorito(clienteId, alojamientoId);
    }

    @Transactional
    public boolean alternarFavorito(Long clienteId, Long alojamientoId) throws ResourceNotFoundException {
        boolean esFav = esFavorito(clienteId, alojamientoId);
        
        if (esFav) {
            eliminarFavorito(clienteId, alojamientoId);
            return false;
        } else {
            agregarFavorito(clienteId, alojamientoId);
            return true;
        }
    }

    private AlojamientoDTO mapToDTO(Alojamiento alojamiento, boolean esFavorito) {
        AlojamientoDTO dto = new AlojamientoDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());
        
        if (alojamiento.getCategoria() != null) {
            dto.setCategoriaId(alojamiento.getCategoria().getId());
        }

        List<String> imagenesUrl = alojamiento.getImagenes().stream()
                .map(ImagenAlojamiento::getUrlImagen)
                .collect(Collectors.toList());
        dto.setImagenesUrl(imagenesUrl);
        
        dto.setEsFavorito(esFavorito);

        List<ReservaDTO> reservasDTO = alojamiento.getReservas().stream()
                .map(ReservaDTO::fromEntity)
                .collect(Collectors.toList());
        dto.setReservas(reservasDTO);

        return dto;
    }
}
