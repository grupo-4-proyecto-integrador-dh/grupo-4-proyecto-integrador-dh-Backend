package com.flavioramses.huellitasbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class ReservaNuevaDTO {
    private Long alojamientoId;
    private Long mascotaId;
    private Long clienteId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDesde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaHasta;

    public ReservaNuevaDTO(Reserva reserva) {
    }

    public static ReservaNuevaDTO toReservaDTO(Reserva reserva){
        return new ReservaNuevaDTO(reserva);
    }
    

}
