package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.model.RolUsuario;
import com.flavioramses.huellitasbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public void saveUsuario(@RequestBody Usuario usuario) {
        usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
    }

    @GetMapping("/rol/{role}")
    public ResponseEntity<List<Usuario>> getUsuariosByRole(@PathVariable RolUsuario role) {
        return ResponseEntity.ok(usuarioService.getUsersByRole(role));
    }

    @PutMapping("/{usuarioId}/rol/{role}")
    public ResponseEntity<String> assignRole(@PathVariable Long usuarioId, @PathVariable RolUsuario role) {
        usuarioService.assignRole(usuarioId, role);
        return ResponseEntity.ok("Rol actualizado correctamente.");
    }

    @PutMapping("/{usuarioId}/revocar-admin")
    public ResponseEntity<String> removeAdminRole(@PathVariable Long usuarioId) {
        usuarioService.removeAdminRole(usuarioId);
        return ResponseEntity.ok("Permiso de administrador revocado correctamente.");
    }
}
