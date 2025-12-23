package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_usuario_id", nullable = false)
    private Usuario clienteUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_usuario_id")
    private Usuario empleadoUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = "hora_inicio_programada", nullable = false)
    private LocalTime horaInicioProgramada;

    @Column(name = "hora_fin_programada", nullable = false)
    private LocalTime horaFinProgramada;

    @Column(name = "hora_inicio_real")
    private LocalDateTime horaInicioReal;

    @Column(name = "hora_fin_real")
    private LocalDateTime horaFinReal;

    @Column(name = "hora_registro")
    private LocalDateTime horaRegistro;

    @Builder.Default
    @Column(columnDefinition = "enum('reservada','confirmada','registrada','en_progreso','completada','cancelada','no_show')")
    private String estado = "reservada";

    @Builder.Default
    @Column(columnDefinition = "enum('web','app_movil','chatbot','telefono','walkin','admin')")
    private String origen = "app_movil";

    @Column(columnDefinition = "TEXT")
    private String notas;

    @Column(columnDefinition = "TEXT")
    private String notasInternas;

    @Column(name = "sesion_chat_ia_id")
    private Long sesionChatIaId;

    @Column(name = "puntaje_confianza_ia")
    private Double puntajeConfianzaIa;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

}
