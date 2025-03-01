package com.flavioramses.huellitasbackend.dto;

import lombok.Data;

@Data
public class UsuarioRegistroDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
}
