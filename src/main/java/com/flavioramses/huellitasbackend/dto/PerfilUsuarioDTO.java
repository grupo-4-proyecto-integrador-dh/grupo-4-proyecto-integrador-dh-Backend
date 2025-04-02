package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilUsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String numeroTelefono;
    
    public static PerfilUsuarioDTO fromEntities(Usuario usuario, Cliente cliente) {
        return new PerfilUsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail(),
            cliente != null ? cliente.getNumeroTelefono() : null
        );
    }
} 