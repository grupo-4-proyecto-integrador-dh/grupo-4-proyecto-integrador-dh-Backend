package com.flavioramses.huellitasbackend.service;

import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente updateCliente(Long id,Cliente clienteNuevo) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if(cliente == null || clienteNuevo == null) return null;

        cliente.setNumeroTelefono(clienteNuevo.getNumeroTelefono());


        return clienteRepository.save(clienteNuevo);
    }

    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteClienteById(Long id) {
        clienteRepository.deleteById(id);
    }
}
