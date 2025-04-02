package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.AlojamientoDashboardDTO;
import com.flavioramses.huellitasbackend.dto.AlojamientoDTO;
import com.flavioramses.huellitasbackend.dto.ReservaDTO;
import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.Categoria;
import com.flavioramses.huellitasbackend.model.ImagenAlojamiento;
import com.flavioramses.huellitasbackend.model.Reserva;
import com.flavioramses.huellitasbackend.repository.AlojamientoRepository;
import com.flavioramses.huellitasbackend.repository.CategoriaRepository;
import com.flavioramses.huellitasbackend.repository.ImagenAlojamientoRepository;
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

    @Autowired
    private ImagenAlojamientoRepository imagenAlojamientoRepository;

    @Transactional
    public Alojamiento crearAlojamiento(AlojamientoDTO alojamientoDTO) throws ResourceNotFoundException {
        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaId()));

        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setCategoria(categoria);

        List<ImagenAlojamiento> imagenes = alojamientoDTO.getImagenesUrl().stream()
                .map(url -> ImagenAlojamiento.builder().urlImagen(url).alojamiento(alojamiento).build())
                .collect(Collectors.toList());
        alojamiento.setImagenes(imagenes);

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

        Categoria categoria = categoriaRepository.findById(alojamientoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + alojamientoDTO.getCategoriaId()));

        alojamiento.setNombre(alojamientoDTO.getNombre());
        alojamiento.setDescripcion(alojamientoDTO.getDescripcion());
        alojamiento.setPrecio(alojamientoDTO.getPrecio());
        alojamiento.setCategoria(categoria);

        imagenAlojamientoRepository.deleteAll(alojamiento.getImagenes());

        List<ImagenAlojamiento> nuevasImagenes = alojamientoDTO.getImagenesUrl().stream()
                .map(url -> ImagenAlojamiento.builder().urlImagen(url).alojamiento(alojamiento).build())
                .collect(Collectors.toList());
        alojamiento.setImagenes(nuevasImagenes);

        return alojamientoRepository.save(alojamiento);
    }

    public AlojamientoDTO getAlojamientoById(Long id) throws ResourceNotFoundException {
        Alojamiento alojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado con ID: " + id));

        return mapToDTO(alojamiento);
    }

    private AlojamientoDTO mapToDTO(Alojamiento alojamiento) {
        AlojamientoDTO dto = new AlojamientoDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());
        if (alojamiento.getCategoria() != null) {
            dto.setCategoriaId(alojamiento.getCategoria().getId());
        } else {
            dto.setCategoriaId(null);
        }

        List<String> imagenesUrl = alojamiento.getImagenes().stream()
                .map(ImagenAlojamiento::getUrlImagen)
                .collect(Collectors.toList());
        dto.setImagenesUrl(imagenesUrl);

        List<ReservaDTO> reservasDTO = alojamiento.getReservas().stream()
                .map(ReservaDTO::fromEntity)
                .collect(Collectors.toList());
        dto.setReservas(reservasDTO);

        return dto;
    }

    @Transactional
    public void eliminarAlojamientoPorId(Long id) {
        alojamientoRepository.deleteById(id);
    }

    public List<Alojamiento> getAllAlojamientos() {
        return alojamientoRepository.findAll();
    }
}