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
    @Column(name = "apellido")
    private String apellido;
    @Column(unique = true)
    private String email;
    @Column(name = "contrasena")
    private String password;

    // Enum
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;



}