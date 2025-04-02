package com.flavioramses.huellitasbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaNuevaDTO {
    @NotNull
    private Long alojamientoId;
    @NotNull
    private Long mascotaId;
    @NotNull
    private Long clienteId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDesde;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaHasta;
}

