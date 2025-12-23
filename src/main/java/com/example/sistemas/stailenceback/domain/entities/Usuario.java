package com.example.sistemas.stailenceback.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String correo;

    @Column(length = 20, unique = true)
    private String telefono;

    @Column(name = "hash_contrasena", nullable = false)
    private String hashContrasena;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    @Column(columnDefinition = "enum('masculino','femenino','otro','prefiero_no_decirlo')")
    private String genero;

    @Builder.Default
    @Column(name = "correo_verificado")
    private Boolean correoVerificado = false;

    @Builder.Default
    @Column(name = "telefono_verificado")
    private Boolean telefonoVerificado = false;

    @Builder.Default
    private Boolean activo = true;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "ultimo_acceso_en")
    private LocalDateTime ultimoAccesoEn;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToMany(mappedBy = "clienteUsuario")
    private List<Cita> citasCliente;

    @OneToMany(mappedBy = "empleadoUsuario")
    private List<Cita> citasEmpleado;

    @OneToMany(mappedBy = "usuario")
    private List<PerfilesUsuario> perfilesUsuario;

}
