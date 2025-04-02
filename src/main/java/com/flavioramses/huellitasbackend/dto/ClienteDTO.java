package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private String numeroTelefono;
    private List<MascotaDTO> mascotas;
    private List<ReservaDTO> reservas;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getUsuario().getEmail();
        this.nombre = cliente.getUsuario().getNombre();
        this.apellido = cliente.getUsuario().getApellido();
        this.numeroTelefono = cliente.getNumeroTelefono();
        this.mascotas = cliente.getMascotas() != null ? MascotaDTO.toMascotaDTOList(cliente.getMascotas()) : new ArrayList<>();
        this.reservas = cliente.getReservas() != null ? ReservaDTO.toReservaDTOList(cliente.getReservas()) : new ArrayList<>();
    }

    public static ClienteDTO toClienteDTO(Cliente cliente) {
        return new ClienteDTO(cliente);
    }

    public static List<ClienteDTO> toClienteDTOList(List<Cliente> clientes) {
        return clientes.stream()
                .map(ClienteDTO::toClienteDTO)
                .collect(Collectors.toList());
    }
}

