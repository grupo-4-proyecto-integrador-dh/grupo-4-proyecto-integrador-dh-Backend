package com.flavioramses.huellitasbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MascotaReservaDTO {
    private String nombre;
    private Long clienteId;

}
