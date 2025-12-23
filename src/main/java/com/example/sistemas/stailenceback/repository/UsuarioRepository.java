package com.example.sistemas.stailenceback.repository;

import com.example.sistemas.stailenceback.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByUuid(String uuid);
    boolean existsByCorreo(String correo);
}

