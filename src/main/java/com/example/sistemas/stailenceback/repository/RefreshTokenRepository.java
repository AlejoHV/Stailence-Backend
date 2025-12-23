package com.example.sistemas.stailenceback.repository;

import com.example.sistemas.stailenceback.domain.entities.RefreshToken;
import com.example.sistemas.stailenceback.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUsuario(Usuario usuario);
}

