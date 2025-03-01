package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.model.RolUsuario;
import com.flavioramses.huellitasbackend.model.Usuario;
import com.flavioramses.huellitasbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById (Long id) {
        return usuarioRepository.findById(id);
    }

    public void saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<Usuario> getUsersByRole(RolUsuario role) {
        return usuarioRepository.findByRol(role);
    }

    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }


    public void assignRole(Long usuarioId, RolUsuario newRole) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setRol(newRole);
        usuarioRepository.save(usuario);
    }

    public void removeAdminRole(Long usuarioId) {
        assignRole(usuarioId, RolUsuario.USUARIO);
    }


}
