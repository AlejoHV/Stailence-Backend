package com.example.sistemas.stailenceback.repository;

import com.example.sistemas.stailenceback.domain.entities.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    Optional<Negocio> findByUuid(String uuid);
    Optional<Negocio> findBySlug(String slug);
    boolean existsBySlug(String slug);
}

