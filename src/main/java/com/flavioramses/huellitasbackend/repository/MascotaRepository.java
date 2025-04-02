package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByClienteId(Long clienteId);
    
    boolean existsByIdAndClienteId(Long id, Long clienteId);
    
    Optional<Mascota> findByIdAndClienteId(Long id, Long clienteId);
}