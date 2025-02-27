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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String firstName;

    @Column(name = "apellido")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "contrasena")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol") 
    private UserRole rol;
}
