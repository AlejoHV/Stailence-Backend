package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles_usuario_negocio")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolesUsuarioNegocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "rol", nullable = false)
    private String rol;
}

