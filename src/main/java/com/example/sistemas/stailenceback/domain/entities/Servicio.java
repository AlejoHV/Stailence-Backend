package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "enum('cabello','barba','spa','color','tratamiento','otro')")
    private String categoria;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(nullable = false)
    private BigDecimal precio;

    @Builder.Default
    @Column(length = 3)
    private String moneda = "COP";

    @Builder.Default
    private Boolean activo = true;

    @Builder.Default
    private Boolean publico = true;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Builder.Default
    private Integer orden = 0;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

}
