package com.flavioramses.huellitasbackend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FechaOcupadaDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}