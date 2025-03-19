package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Reservaciones por rangos de fechas
    @Query("SELECT r FROM Reserva r WHERE r.fechaDesde >= :fechaInicio AND r.fechaHasta <= :fechaFin")
    List<Reserva> findByFechaDesdeBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // Reservas disponibles en el rango de fechas especificado
    @Query("""
    SELECT a FROM Alojamiento a 
    WHERE a.id NOT IN (
        SELECT r.alojamiento.id FROM Reserva r 
        WHERE (r.fechaDesde <= :fechaFin AND r.fechaHasta >= :fechaInicio)
    )
    """)

    List<Alojamiento> findAlojamientosDisponibles(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );





}
