package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.model.Categoria;
import com.flavioramses.huellitasbackend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

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

    public Categoria updateCategoria(Long id, Categoria categoriaNueva) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if(categoria == null || categoriaNueva == null) return null;

        categoria.setNombre(categoriaNueva.getNombre());
        categoria.setDescripcion(categoriaNueva.getDescripcion());

        return categoriaRepository.save(categoria);
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoriaById(Long id) {
        categoriaRepository.deleteById(id);
    }
}
