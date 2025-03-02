package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.model.Categoria;
import com.flavioramses.huellitasbackend.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/{id}")
    public Optional<Categoria> getCategoriaById(@PathVariable Long id) {
        return categoriaService.getCategoriaById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) throws BadRequestException {
        try{
            return ResponseEntity.ok(categoriaService.updateCategoria(id,categoria));
        }catch (Exception e){
            throw new BadRequestException("Ocurrio un error al actualizar la categoria");
        }
    }

    @PostMapping
    public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) throws BadRequestException {
        Categoria categoriaGuardado = categoriaService.saveCategoria(categoria);
        Optional<Categoria> categoriaById = categoriaService.getCategoriaById(categoria.getId());
        if(categoriaById.isPresent()){
            return ResponseEntity.ok(categoriaGuardado);
        } else {
            throw new BadRequestException("Hubo un error al agregar la categoria");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCategoriaById(@PathVariable Long id) {
        categoriaService.deleteCategoriaById(id);
    }

}
