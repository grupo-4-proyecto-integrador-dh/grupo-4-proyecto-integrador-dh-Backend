package com.flavioramses.huellitasbackend.repository;

import com.flavioramses.huellitasbackend.model.AlojamientoFavorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlojamientoFavoritoRepository extends JpaRepository<AlojamientoFavorito, Long> {
    
    @Query("SELECT af FROM AlojamientoFavorito af JOIN FETCH af.alojamiento a LEFT JOIN FETCH a.imagenes WHERE af.cliente.id = :clienteId")
    List<AlojamientoFavorito> findByClienteIdWithAlojamiento(@Param("clienteId") Long clienteId);
    
    boolean existsByClienteIdAndAlojamientoId(Long clienteId, Long alojamientoId);
    
    Optional<AlojamientoFavorito> findByClienteIdAndAlojamientoId(Long clienteId, Long alojamientoId);

    @Modifying
    @Query("DELETE FROM AlojamientoFavorito af WHERE af.cliente.id = :clienteId AND af.alojamiento.id = :alojamientoId")
    void deleteByClienteIdAndAlojamientoId(@Param("clienteId") Long clienteId, @Param("alojamientoId") Long alojamientoId);
    
    @Query("SELECT CASE WHEN COUNT(af) > 0 THEN true ELSE false END FROM AlojamientoFavorito af WHERE af.cliente.id = :clienteId AND af.alojamiento.id = :alojamientoId")
    boolean esFavorito(@Param("clienteId") Long clienteId, @Param("alojamientoId") Long alojamientoId);
}