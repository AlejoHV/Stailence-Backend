package com.example.sistemas.stailenceback.security;

import com.example.sistemas.stailenceback.domain.entities.Usuario;
import com.example.sistemas.stailenceback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + username));

        // Mapear roles/permissions si existen. Aquí asumimos que hay perfiles en otra entidad.
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(usuario.getCorreo(), usuario.getHashContrasena(), authorities);
    }
}

