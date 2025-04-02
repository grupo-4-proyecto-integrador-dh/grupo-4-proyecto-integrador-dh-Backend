package com.flavioramses.huellitasbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaReservaDTO {
    private String nombre;
    private Long clienteId;
    private String especie;
    private String raza;
    private Double peso;
    private Integer edad;
    private String observaciones;
    private Boolean activo;

    public MascotaReservaDTO(String nombre, Long clienteId) {
        this.nombre = nombre;
        this.clienteId = clienteId;
        this.activo = true;
    }
}
