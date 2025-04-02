package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.ClienteDTO;
import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "clientes")
public class ClienteController {
    @Autowired
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    @PostMapping
    public ResponseEntity<ClienteDTO> saveCliente(@RequestBody Cliente cliente) throws BadRequestException {
        Cliente clienteGuardado = clienteService.saveCliente(cliente);
        return ResponseEntity.ok(ClienteDTO.toClienteDTO(clienteGuardado));

    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.status(200).body(ClienteDTO.toClienteDTOList(clienteService.getAllClientes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Cliente> clienteBuscado = clienteService.getClienteById(id);
        if(clienteBuscado.isPresent()){
            return ResponseEntity.ok(
                    ClienteDTO.toClienteDTO(clienteBuscado.get())
            );
        }else{
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id,@RequestBody Cliente cliente) throws BadRequestException {
        try{
            return ResponseEntity.ok(ClienteDTO.toClienteDTO(clienteService.updateCliente(id,cliente)));
        }catch (Exception e){
            throw new BadRequestException("Ocurrio un error al actualizar el cliente");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable("id") Long id) {
        clienteService.deleteClienteById(id);
        return ResponseEntity.status(204).build();
    }
}
