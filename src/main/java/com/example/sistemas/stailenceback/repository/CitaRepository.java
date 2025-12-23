package com.example.sistemas.stailenceback.repository;

import com.example.sistemas.stailenceback.domain.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByNegocioIdAndFechaProgramada(Long negocioId, LocalDate fechaProgramada);
    List<Cita> findByClienteUsuarioId(Long clienteId);
    List<Cita> findByEmpleadoUsuarioIdAndFechaProgramada(Long empleadoId, LocalDate fecha);
}

