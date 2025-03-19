package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClienteDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private String numeroTelefono;
    private List<Mascota> mascotas;
    private List<Reserva> reservas;


    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getUsuario().getEmail();
        this.nombre = cliente.getUsuario().getNombre();
        this.apellido = cliente.getUsuario().getApellido();
        this.numeroTelefono = cliente.getNumeroTelefono();
        this.mascotas = cliente.getMascotas();
        this.reservas = cliente.getReservas();
    }

    public static ClienteDTO toClienteDTO(Cliente cliente){
        return new ClienteDTO(cliente);
    }

    public static List<ClienteDTO> toUserDTOList(List<Cliente> clientes) {
        return clientes.stream()
                .map(ClienteDTO::toClienteDTO)
                .collect(Collectors.toList());
    }
}
