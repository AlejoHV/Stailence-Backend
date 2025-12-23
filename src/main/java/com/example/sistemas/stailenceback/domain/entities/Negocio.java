package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "negocios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @Column(name = "nombre_legal")
    private String nombreLegal;

    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    private String ciudad;
    private String departamento;

    @Builder.Default
    @Column(nullable = false)
    private String pais = "Colombia";

    @Builder.Default
    @Column(name = "zona_horaria")
    private String zonaHoraria = "America/Bogota";

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    @Column(length = 3)
    private String moneda = "COP";

    @Builder.Default
    @Column(length = 2)
    private String idioma = "es";

    @Builder.Default
    @Column(columnDefinition = "enum('pendiente','activo','suspendido','cerrado')")
    private String estado = "pendiente";

    @Column(name = "verificado_en")
    private LocalDateTime verificadoEn;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    private List<RolesUsuarioNegocio> rolesUsuarioNegocio;
}
