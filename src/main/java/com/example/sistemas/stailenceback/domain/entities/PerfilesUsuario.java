package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfiles_usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilesUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "rol", nullable = false)
    private String rol;

}

