package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.PerfilUsuarioDTO;
import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Usuario;
import com.flavioramses.huellitasbackend.repository.ClienteRepository;
import com.flavioramses.huellitasbackend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerfilService {
    
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    
    public PerfilUsuarioDTO obtenerPerfil(Long usuarioId) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));
        
        Cliente cliente = clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente para usuario con id " + usuarioId + " no encontrado"));
        
        return PerfilUsuarioDTO.fromEntities(usuario, cliente);
    }
    
    @Transactional
    public PerfilUsuarioDTO actualizarPerfil(Long usuarioId, PerfilUsuarioDTO perfilDTO) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + usuarioId + " no encontrado"));
        
        Cliente cliente = clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente para usuario con id " + usuarioId + " no encontrado"));
        
        usuario.setNombre(perfilDTO.getNombre());
        usuario.setApellido(perfilDTO.getApellido());
        usuario.setEmail(perfilDTO.getEmail());
        
        cliente.setNumeroTelefono(perfilDTO.getNumeroTelefono());
        
        usuarioRepository.save(usuario);
        clienteRepository.save(cliente);
        
        return PerfilUsuarioDTO.fromEntities(usuario, cliente);
    }
} 