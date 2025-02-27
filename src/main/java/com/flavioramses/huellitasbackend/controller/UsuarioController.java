package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.flavioramses.huellitasbackend.model.Usuario;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{Id}")
    public Optional<Usuario> getUsuarioById(@PathVariable("Id") Long usuarioId) {
        return usuarioService.getUsuarioById(usuarioId);
    }

    @PostMapping
    public void saveUsuario(@RequestBody Usuario usuario) {
        usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("/{Id}")
    public void deleteUsuarioById(@PathVariable("Id") Long usuarioId) {
        usuarioService.deleteUsuarioById(usuarioId);
    }


}
