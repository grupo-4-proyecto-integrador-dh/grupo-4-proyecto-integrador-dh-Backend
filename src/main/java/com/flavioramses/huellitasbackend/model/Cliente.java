package com.flavioramses.huellitasbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroTelefono;
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Mascota> mascotas = new ArrayList<>();
}
