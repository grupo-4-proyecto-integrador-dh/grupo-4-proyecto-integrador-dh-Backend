package com.flavioramses.huellitasbackend.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class DisponibilidadBusquedaDTO {
    @NotNull
    private LocalDate fechaInicio;
    @NotNull
    private LocalDate fechaFin;
    private String nombreAlojamiento;
}