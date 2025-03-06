package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.AlojamientoDashboardDTO;
import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.Categoria;
import com.flavioramses.huellitasbackend.repository.AlojamientoRepository;
import com.flavioramses.huellitasbackend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlojamientoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    public Alojamiento crearAlojamiento(AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException {
        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaId()));

        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImagenUrl(alojamientoDTO.getImagenUrl());
        alojamiento.setCategoria(categoria);

        return alojamientoRepository.save(alojamiento);
    }

    public List<AlojamientoDashboardDTO> obtenerAlojamientosDashboardDTO() {
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        return alojamientos.stream()
                .map(this::convertirADashboardDTO)
                .collect(Collectors.toList());
    }

    private AlojamientoDashboardDTO convertirADashboardDTO(Alojamiento alojamiento) {
        return AlojamientoDashboardDTO.toAlojamientoDashboardDTO(alojamiento);
    }

    public Alojamiento actualizarAlojamiento(Long id, AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException {
        Alojamiento alojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + id));

        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaId()));

        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImagenUrl(alojamientoDTO.getImagenUrl());
        alojamiento.setCategoria(categoria);

        return alojamientoRepository.save(alojamiento);
    }

    public Optional<Alojamiento> obtenerAlojamientoPorId(Long id) {
        return alojamientoRepository.findById(id);
    }

    public void eliminarAlojamientoPorId(Long id) {
        alojamientoRepository.deleteById(id);
    }

    public List<Alojamiento> getAllAlojamientos() {
        return alojamientoRepository.findAll();
    }
}