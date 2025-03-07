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
import org.springframework.transaction.annotation.Transactional;

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
        // Verifica si la lista de categorías no está vacía
        if (alojamientoDTO.getCategoriaIds().isEmpty()) {
            throw new ResourceNotFoundException("Debe proporcionar al menos una categoría");
        }

        // Obtiene las categorías por sus IDs
        List<Categoria> categorias = categoriaRepository.findAllById(alojamientoDTO.getCategoriaIds());
        if (categorias.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron categorías con los IDs proporcionados");
        }

        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImagenUrl(alojamientoDTO.getImagenUrl());
        alojamiento.setCategorias(categorias);

        return alojamientoRepository.save(alojamiento);
    }

    public List<AlojamientoDashboardDTO> getAlojamientosDashboardDTO() {
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        return alojamientos.stream()
                .map(this::convertToDashboardDTO)
                .collect(Collectors.toList());
    }

    private AlojamientoDashboardDTO convertToDashboardDTO(Alojamiento alojamiento) {
        return AlojamientoDashboardDTO.toAlojamientoDashboardDTO(alojamiento);
    }

    @Transactional
    public Alojamiento actualizarAlojamiento(Long id, AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException {
        Alojamiento alojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + id));

        if (alojamientoDTO.getCategoriaIds().isEmpty()) {
            throw new ResourceNotFoundException("Debe proporcionar al menos una categoría");
        }

        // Obtiene las categorías por sus IDs
        Categoria categorias = categoriaRepository.findAllById(alojamientoDTO.getCategoriaIds());
        if (categorias.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron categorías con los IDs proporcionados");
        }

        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImagenUrl(alojamientoDTO.getImagenUrl());
        alojamiento.setCategoria(categorias);

        return alojamientoRepository.save(alojamiento);
    }

    public Optional<Alojamiento> getAlojamientoById(Long id) {
        return alojamientoRepository.findById(id);
    }

    @Transactional
    public void eliminarAlojamientoPorId(Long id) {
        alojamientoRepository.deleteById(id);
    }

    public List<Alojamiento> getAllAlojamientos() {
        return alojamientoRepository.findAll();
    }
}