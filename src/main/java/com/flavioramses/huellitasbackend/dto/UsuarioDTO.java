package com.flavioramses.huellitasbackend.dto;
import com.flavioramses.huellitasbackend.model.RolUsuario;
import com.flavioramses.huellitasbackend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private RolUsuario rol;

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }

    public static List<UsuarioDTO> toUserDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioDTO::toUsuarioDTO)
                .collect(Collectors.toList());
    }
}