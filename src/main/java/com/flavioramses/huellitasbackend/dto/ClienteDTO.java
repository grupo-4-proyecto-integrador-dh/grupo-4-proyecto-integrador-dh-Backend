package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Cliente;
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

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.email = cliente.getUsuario().getEmail();
        this.nombre = cliente.getUsuario().getNombre();
        this.apellido = cliente.getUsuario().getApellido();
        this.numeroTelefono = cliente.getNumeroTelefono();
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
