package com.flavioramses.huellitasbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @JsonManagedReference(value = "reserva-mascota")
    @OneToMany(mappedBy = "mascota")
    private List<Reserva> reservas;
    // TODO: Cliente ~ Usuario
}
