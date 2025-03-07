package com.flavioramses.huellitasbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String contrasena;

    // Enum
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;
}