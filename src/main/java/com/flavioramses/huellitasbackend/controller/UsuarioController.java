package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.UsuarioDTO;
import com.flavioramses.huellitasbackend.model.Usuario;
import com.flavioramses.huellitasbackend.model.RolUsuario;
import com.flavioramses.huellitasbackend.security.SecurityConfig;
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
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<UsuarioDTO> usuarioDTOs = UsuarioDTO.toUserDTOList(usuarios);
        return ResponseEntity.ok(usuarioDTOs);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuarioById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);

        if(usuario.isEmpty()) throw new ResourceNotFoundException("Usuario con id "+id+" no encontrado");

        return UsuarioDTO.toUsuarioDTO(usuario.get());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> saveUsuario(@RequestBody Usuario usuario) throws BadRequestException {
        String contrasenaEncriptada = new SecurityConfig().passwordEncoder().encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaEncriptada);
        Usuario usuarioGuardado = usuarioService.saveUsuario(usuario);
        Optional<Usuario> usuarioById = usuarioService.getUsuarioById(usuario.getId());
        if(usuarioById.isPresent()){
            return ResponseEntity.ok(UsuarioDTO.toUsuarioDTO(usuarioGuardado));
        } else {
            throw new BadRequestException("Hubo un error al registrar el usuario");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
    }

    @GetMapping("/rol/{role}")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosByRole(@PathVariable RolUsuario role) {
        List<UsuarioDTO> usuarioDTOs = UsuarioDTO.toUserDTOList(usuarioService.getUsersByRole(role));
        return ResponseEntity.ok(usuarioDTOs);
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
