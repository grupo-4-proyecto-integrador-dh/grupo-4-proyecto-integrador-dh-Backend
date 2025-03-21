package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.EmailAlreadyExistsException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.UsuarioDTO;
import com.flavioramses.huellitasbackend.dto.UsuarioLoginDTO;
import com.flavioramses.huellitasbackend.dto.UsuarioRegistroDTO;
import com.flavioramses.huellitasbackend.model.Usuario;
import com.flavioramses.huellitasbackend.security.JwtTokenProvider;
import com.flavioramses.huellitasbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flavioramses.huellitasbackend.service.EmailService;
import jakarta.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody UsuarioRegistroDTO dto) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(dto);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getContrasena()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwt);
            emailService.sendRegistrationConfirmation(nuevoUsuario.getEmail(), nuevoUsuario.getNombre());
            response.put("usuario", UsuarioDTO.toUsuarioDTO(nuevoUsuario));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UsuarioLoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getContrasena()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            Usuario usuario = usuarioService.getUsuarioByEmail(loginRequest.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwt);
            response.put("usuario", UsuarioDTO.toUsuarioDTO(usuario));

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos");
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}