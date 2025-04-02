package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.PerfilUsuarioDTO;
import com.flavioramses.huellitasbackend.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.flavioramses.huellitasbackend.service.UsuarioService;

@RestController
@RequestMapping("/perfil")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Perfil", description = "Operaciones relacionadas con el perfil del usuario")
public class PerfilController {
    
    private final PerfilService perfilService;
    private final UsuarioService usuarioService;
    
    @GetMapping
    @Operation(summary = "Obtener perfil de usuario", description = "Obtiene los datos del perfil del usuario autenticado")
    public ResponseEntity<PerfilUsuarioDTO> obtenerPerfilUsuario(@AuthenticationPrincipal UserDetails userDetails) 
            throws ResourceNotFoundException {
        Long usuarioId = usuarioService.getUsuarioByEmail(userDetails.getUsername()).getId();
        PerfilUsuarioDTO perfil = perfilService.obtenerPerfil(usuarioId);
        return ResponseEntity.ok(perfil);
    }
    
    @PutMapping
    @Operation(summary = "Actualizar perfil de usuario", description = "Actualiza los datos del perfil del usuario autenticado")
    public ResponseEntity<PerfilUsuarioDTO> actualizarPerfilUsuario(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "Datos actualizados del perfil", required = true) @RequestBody PerfilUsuarioDTO perfilDTO) 
            throws ResourceNotFoundException {
        Long usuarioId = usuarioService.getUsuarioByEmail(userDetails.getUsername()).getId();
        PerfilUsuarioDTO perfilActualizado = perfilService.actualizarPerfil(usuarioId, perfilDTO);
        return ResponseEntity.ok(perfilActualizado);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar perfil por ID", description = "Actualiza los datos del perfil usando el ID del usuario directamente")
    public ResponseEntity<PerfilUsuarioDTO> actualizarPerfilPorId(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados del perfil", required = true) @RequestBody PerfilUsuarioDTO perfilDTO) 
            throws ResourceNotFoundException {
        PerfilUsuarioDTO perfilActualizado = perfilService.actualizarPerfil(id, perfilDTO);
        return ResponseEntity.ok(perfilActualizado);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener perfil por ID", description = "Obtiene los datos del perfil por ID de usuario")
    public ResponseEntity<PerfilUsuarioDTO> obtenerPerfilPorId(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long id) 
            throws ResourceNotFoundException {
        PerfilUsuarioDTO perfil = perfilService.obtenerPerfil(id);
        return ResponseEntity.ok(perfil);
    }
} 