package com.flavioramses.huellitasbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIgnore
    @OneToMany(mappedBy = "mascota")
    private List<Reserva> reservas;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    private String especie;
    private String raza;
    private Double peso;
    private Integer edad;
    private String observaciones;
    private boolean activo = true;
}
