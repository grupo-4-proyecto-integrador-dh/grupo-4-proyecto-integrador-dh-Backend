package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {

    @Query("""
    SELECT a FROM Alojamiento a
    WHERE a.activo = true 
    AND LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) 
    AND NOT EXISTS (
        SELECT 1 FROM Reserva r 
        WHERE r.alojamiento.id = a.id 
        AND r.fechaDesde <= :fechaFin AND r.fechaHasta >= :fechaInicio 
        AND r.estado IN ('PENDIENTE', 'CONFIRMADA')
    )
    ORDER BY a.nombre ASC
    """)
    List<Alojamiento> findDisponiblesByFechasAndNombre(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            @Param("nombre") String nombre);

    @Query("""
    SELECT a FROM Alojamiento a
    WHERE a.activo = true 
    AND NOT EXISTS (
        SELECT 1 FROM Reserva r 
        WHERE r.alojamiento.id = a.id 
        AND r.fechaDesde <= :fechaFin AND r.fechaHasta >= :fechaInicio 
        AND r.estado IN ('PENDIENTE', 'CONFIRMADA')
    )
    ORDER BY a.nombre ASC
    """)
    List<Alojamiento> findDisponiblesByFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
            
    @Query("""
    SELECT COUNT(r) = 0 FROM Reserva r 
    WHERE r.alojamiento.id = :alojamientoId
    AND r.fechaDesde <= :fechaFin 
    AND r.fechaHasta >= :fechaInicio 
    AND r.estado IN ('PENDIENTE', 'CONFIRMADA')
    """)
    boolean isDisponibleEnFechas(
            @Param("alojamientoId") Long alojamientoId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}

