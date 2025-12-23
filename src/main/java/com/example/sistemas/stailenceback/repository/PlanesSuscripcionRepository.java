package com.example.sistemas.stailenceback.repository;

import com.example.sistemas.stailenceback.domain.entities.PlanesSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanesSuscripcionRepository extends JpaRepository<PlanesSuscripcion, Long> {
}
