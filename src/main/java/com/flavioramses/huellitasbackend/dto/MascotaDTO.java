package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Mascota;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MascotaDTO {
    private Long id;
    private String nombre;
    private Long clienteId;

    public MascotaDTO(Mascota mascota) {
        this.id = mascota.getId();
        this.nombre = mascota.getNombre();
        this.clienteId = mascota.getCliente().getId();
    }

    public static MascotaDTO toMascotaDTO(Mascota mascota){
        return new MascotaDTO(mascota);
    }

    public static List<MascotaDTO> toMascotaDTOList(List<Mascota> mascotas) {
        return mascotas.stream()
                .map(MascotaDTO::toMascotaDTO)
                .collect(Collectors.toList());
    }
    
}
