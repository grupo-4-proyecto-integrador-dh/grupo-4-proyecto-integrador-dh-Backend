package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Mascota;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {
    private Long id;
    private String nombre;
    private Long clienteId;
    private String especie;
    private String raza;
    private Double peso;
    private Integer edad;
    private String observaciones;
    private Boolean activo;

    public static MascotaDTO toMascotaDTO(Mascota mascota) {
        return new MascotaDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getCliente().getId(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getPeso(),
                mascota.getEdad(),
                mascota.getObservaciones(),
                mascota.isActivo()
        );
    }

    public static List<MascotaDTO> toMascotaDTOList(List<Mascota> mascotas) {
        return mascotas.stream()
                .map(MascotaDTO::toMascotaDTO)
                .collect(Collectors.toList());
    }
}
