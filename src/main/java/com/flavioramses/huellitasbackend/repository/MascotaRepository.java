package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
