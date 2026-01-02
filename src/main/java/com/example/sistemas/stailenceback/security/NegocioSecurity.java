package com.example.sistemas.stailenceback.security;

import com.example.sistemas.stailenceback.domain.entities.MembershipRole;
import com.example.sistemas.stailenceback.repository.RolesUsuarioNegocioRepository;
import com.example.sistemas.stailenceback.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("negocioSecurity")
public class NegocioSecurity {

    private final UsuarioRepository usuarioRepository;
    private final RolesUsuarioNegocioRepository rolesUsuarioNegocioRepository;

    public NegocioSecurity(UsuarioRepository usuarioRepository, RolesUsuarioNegocioRepository rolesUsuarioNegocioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolesUsuarioNegocioRepository = rolesUsuarioNegocioRepository;
    }

    public boolean isOwner(Authentication authentication, Long negocioId) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        String username = authentication.getName();
        return usuarioRepository.findByCorreo(username)
                .map(u -> rolesUsuarioNegocioRepository.existsByNegocioIdAndUsuarioIdAndRol(negocioId, u.getId(), MembershipRole.OWNER))
                .orElse(false);
    }

    public boolean hasRole(Authentication authentication, Long negocioId, String role) {
        if (authentication == null || !authentication.isAuthenticated()) return false;
        String username = authentication.getName();
        return usuarioRepository.findByCorreo(username)
                .map(u -> {
                    try {
                        MembershipRole mr = MembershipRole.valueOf(role);
                        return rolesUsuarioNegocioRepository.existsByNegocioIdAndUsuarioIdAndRol(negocioId, u.getId(), mr);
                    } catch (IllegalArgumentException ex) {
                        return false;
                    }
                })
                .orElse(false);
    }
}
