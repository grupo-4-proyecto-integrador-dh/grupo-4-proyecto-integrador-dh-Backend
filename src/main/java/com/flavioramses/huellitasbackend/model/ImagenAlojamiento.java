package com.flavioramses.huellitasbackend.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "imagenes_alojamiento")
public class ImagenAlojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_imagen")
    private String urlImagen;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id")
    @JsonBackReference
    private Alojamiento alojamiento;
}