package com.example.sistemas.stailenceback.repository;

import com.example.sistemas.stailenceback.domain.entities.RolesUsuarioNegocio;
import com.example.sistemas.stailenceback.domain.entities.MembershipRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesUsuarioNegocioRepository extends JpaRepository<RolesUsuarioNegocio, Long> {

    // Buscar todas las relaciones de un negocio
    List<RolesUsuarioNegocio> findByNegocioId(Long negocioId);

    // Buscar la relación específica entre negocio y usuario
    Optional<RolesUsuarioNegocio> findByNegocioIdAndUsuarioId(Long negocioId, Long usuarioId);

    // Verificar existencia de rol específico para un usuario en un negocio
    boolean existsByNegocioIdAndUsuarioIdAndRol(Long negocioId, Long usuarioId, MembershipRole rol);

    // Contar cuántos usuarios tienen un rol determinado en un negocio (útil para no eliminar el último OWNER)
    long countByNegocioIdAndRol(Long negocioId, MembershipRole rol);
}
