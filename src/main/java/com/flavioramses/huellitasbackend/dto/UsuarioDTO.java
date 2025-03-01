package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.email = usuario.getEmail();
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario){
        return new UsuarioDTO(usuario);
    }

    public static List<UsuarioDTO> toUserDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioDTO::toUsuarioDTO)
                .collect(Collectors.toList());
    }
}
