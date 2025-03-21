package com.flavioramses.huellitasbackend.controller;

import com.flavioramses.huellitasbackend.Exception.BadRequestException;
import com.flavioramses.huellitasbackend.Exception.ResourceNotFoundException;
import com.flavioramses.huellitasbackend.dto.MascotaDTO;
import com.flavioramses.huellitasbackend.dto.MascotaReservaDTO;
import com.flavioramses.huellitasbackend.model.Cliente;
import com.flavioramses.huellitasbackend.model.Mascota;
import com.flavioramses.huellitasbackend.service.ClienteService;
import com.flavioramses.huellitasbackend.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final ClienteService clienteService;

    @Autowired
    public MascotaController(MascotaService mascotaService, ClienteService clienteService) {
        this.mascotaService = mascotaService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<MascotaDTO> getAllMascotas() {
        return MascotaDTO.toMascotaDTOList(mascotaService.getAllMascotas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> getMascotaById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Mascota> mascotaBuscada = mascotaService.getMascotaById(id);
        if(mascotaBuscada.isPresent()){
            return ResponseEntity.ok(
                    MascotaDTO.toMascotaDTO(mascotaBuscada.get())
            );
        }else{
            throw new ResourceNotFoundException("Mascota no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> updateMascota(@PathVariable Long id, @RequestBody Mascota mascota) throws BadRequestException {
        try{
            return ResponseEntity.ok(
                    MascotaDTO.toMascotaDTO(
                            mascotaService.updateMascota(id,mascota)
                    )
            );
        }catch (Exception e){
            throw new BadRequestException("Ocurrio un error al actualizar la mascota");
        }
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> saveMascota(@RequestBody MascotaReservaDTO mascotaRequest) throws BadRequestException {
        // Validar que el nombre y el clienteId no sean nulos o vacíos
        if (mascotaRequest.getNombre() == null || mascotaRequest.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la mascota no puede estar vacío.");
        }
        if (mascotaRequest.getClienteId() == null) {
            throw new BadRequestException("El ID del cliente no puede estar vacío.");
        }

        // Obtén el cliente desde la base de datos usando el clienteId
        Cliente cliente = clienteService.getClienteById(mascotaRequest.getClienteId())
                .orElseThrow(() -> new BadRequestException("Cliente no encontrado con ID: " + mascotaRequest.getClienteId()));

        // Crea la mascota y asocia el cliente
        Mascota mascota = new Mascota();
        mascota.setNombre(mascotaRequest.getNombre());
        mascota.setCliente(cliente);

        // Guarda la mascota
        Mascota mascotaGuardada = mascotaService.saveMascota(mascota);

        // Verifica que la mascota se haya guardado correctamente
        Optional<Mascota> mascotaById = mascotaService.getMascotaById(mascotaGuardada.getId());
        if (mascotaById.isPresent()) {
            return ResponseEntity.ok(MascotaDTO.toMascotaDTO(mascotaGuardada));
        } else {
            throw new BadRequestException("Hubo un error al registrar la mascota");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMascotaById(@PathVariable Long id) {
        mascotaService.deleteMascotaById(id);
    }

}
