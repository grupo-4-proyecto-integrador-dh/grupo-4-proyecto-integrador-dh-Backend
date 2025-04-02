package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.UsuarioDTO;
import com.flavioramses.huellitasbackend.model.RolUsuario;
import com.flavioramses.huellitasbackend.model.Usuario;
import com.flavioramses.huellitasbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.flavioramses.huellitasbackend.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(UsuarioDTO.toUserDTOList(usuarioService.getAllUsuarios()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(UsuarioDTO.toUsuarioDTO(usuarioService.getUsuarioById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no encontrado"))));
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<String> getUsuarioRol(@PathVariable Long id) throws ResourceNotFoundException {
        Usuario usuario = usuarioService.getUsuarioById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no encontrado"));
        return ResponseEntity.ok(usuario.getRol().name());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> saveUsuario(@RequestBody Usuario usuario) throws BadRequestException {
        Usuario usuarioGuardado = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(UsuarioDTO.toUsuarioDTO(usuarioGuardado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(
            @PathVariable Long id,
            @RequestBody UsuarioDTO usuarioDTO) throws ResourceNotFoundException {

        Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuarioDTO);
        return ResponseEntity.ok(UsuarioDTO.toUsuarioDTO(usuarioActualizado));
    }


    @GetMapping("/resend-confirmation-email/{email}")
    public ResponseEntity<String> resendConfirmationEmail(@PathVariable String email) throws ResourceNotFoundException, BadRequestException {
        Optional<Usuario> userOptional = Optional.ofNullable(usuarioService.getUsuarioByEmail(email));

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Usuario con email "+email+" no encontrado");
        }

        Usuario user = userOptional.get();

        try {
            emailService.sendRegistrationConfirmation(user.getEmail(), user.getNombre());
            return ResponseEntity.ok("Correo de confirmación reenviado exitosamente");
        } catch (MessagingException e) {
            throw new BadRequestException("Hubo un error al reenviar correo de registro");
        }
    }
}