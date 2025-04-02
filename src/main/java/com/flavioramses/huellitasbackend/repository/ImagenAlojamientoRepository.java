package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.ImagenAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenAlojamientoRepository extends JpaRepository<ImagenAlojamiento, Long> {
}