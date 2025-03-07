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

    @Transactional
    public Alojamiento crearAlojamiento(AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException {
        // Verifica si el ID de la categoría es nulo
        if (alojamientoDTO.getCategoriaIds() == null) {
            throw new ResourceNotFoundException("Debe proporcionar un ID de categoría");
        }

        // Obtiene la categoría por su ID
        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getCategoriaIds())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaIds()));

        // Crea el alojamiento
        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImagenUrl(alojamientoDTO.getImagenUrl());
        alojamiento.setCategoria(categoria); // Asigna la categoría encontrada

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
        // Busca el alojamiento por su ID
        Alojamiento alojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + id));

        // Verifica si el ID de la categoría es nulo
        if (alojamientoDTO.getCategoriaIds() == null) {
            throw new ResourceNotFoundException("Debe proporcionar un ID de categoría");
        }

        // Obtiene la categoría por su ID
        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getCategoriaIds())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaIds()));

        // Actualiza los campos del alojamiento
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setImagenUrl(alojamientoDTO.getImagenUrl());
        alojamiento.setCategoria(categoria); // Asigna la categoría encontrada

        return alojamientoRepository.save(alojamiento);

    }

    public Optional<Alojamiento> getAlojamientoById (Long id) {
        return alojamientoRepository.findById(id);
    }

    public void deleteAlojamientoById(Long id) {
        alojamientoRepository.deleteById(id);
    }
}
