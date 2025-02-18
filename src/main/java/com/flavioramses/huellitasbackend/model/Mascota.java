package com.flavioramses.huellitasbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    // TODO: Cliente ~ Usuario
}
