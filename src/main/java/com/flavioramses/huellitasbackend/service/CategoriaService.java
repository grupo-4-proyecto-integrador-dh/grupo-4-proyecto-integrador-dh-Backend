package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.model.Categoria;
import com.flavioramses.huellitasbackend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> getCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria updateCategoria(Long id, Categoria categoriaNueva) throws ResourceNotFoundException {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    if (categoriaNueva == null) {
                        throw new IllegalArgumentException("La nueva categoría no puede ser nula.");
                    }
                    categoria.setNombre(categoriaNueva.getNombre());
                    categoria.setDescripcion(categoriaNueva.getDescripcion());
                    categoria.setImagenUrl(categoriaNueva.getImagenUrl()); // Permite actualizar la URL de la imagen
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }

    @Transactional
    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deleteCategoriaById(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Transactional
    public Categoria updateCategoriaImagen(Long id, String imagenUrl) throws ResourceNotFoundException {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setImagenUrl(imagenUrl);
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }
}
