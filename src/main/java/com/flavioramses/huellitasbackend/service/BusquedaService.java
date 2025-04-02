package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.ImagenAlojamiento;
import com.flavioramses.huellitasbackend.repository.AlojamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BusquedaService {
    
    private final AlojamientoRepository alojamientoRepository;

    @Autowired
    public BusquedaService(AlojamientoRepository alojamientoRepository) {
        this.alojamientoRepository = alojamientoRepository;
    }

    @Transactional(readOnly = true)
    public List<AlojamientoDTO> buscarAlojamientosDisponibles(LocalDate fechaInicio, LocalDate fechaFin, String nombre) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha fin");
        }

        List<Alojamiento> alojamientos;
        if (nombre != null && !nombre.trim().isEmpty()) {
            alojamientos = alojamientoRepository.findDisponiblesByFechasAndNombre(fechaInicio, fechaFin, nombre.trim());
        } else {
            alojamientos = alojamientoRepository.findDisponiblesByFechas(fechaInicio, fechaFin);
        }

        return alojamientos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public boolean verificarDisponibilidad(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha fin");
        }
        
        return alojamientoRepository.isDisponibleEnFechas(alojamientoId, fechaInicio, fechaFin);
    }


    private AlojamientoDTO convertirADTO(Alojamiento alojamiento) {
        return new AlojamientoDTO(
                alojamiento.getId(),
                alojamiento.getNombre(),
                alojamiento.getDescripcion(),
                alojamiento.getPrecio(),
                alojamiento.getCategoria().getId(),
                alojamiento.getImagenes().stream()
                    .map(ImagenAlojamiento::getUrlImagen)
                    .collect(Collectors.toList()),
                new ArrayList<>()
        );
    }
}

