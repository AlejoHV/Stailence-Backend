package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "planes_suscripcion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanesSuscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "precio_mensual", nullable = false)
    private BigDecimal precioMensual;

    @Column(name = "precio_anual", nullable = false)
    private BigDecimal precioAnual;

    @Builder.Default
    @Column(name = "max_empleados")
    private Integer maxEmpleados = 1;

    @Builder.Default
    @Column(name = "max_servicios")
    private Integer maxServicios = 10;

    @Builder.Default
    @Column(name = "max_sucursales")
    private Integer maxSucursales = 1;

    @Column(name = "caracteristicas_ia", columnDefinition = "json")
    private String caracteristicasIa;

    @Builder.Default
    @Column(name = "nivel_soporte")
    private String nivelSoporte = "basico";

    @Builder.Default
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;
}
