package com.example.sistemas.stailenceback.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDetailResponse {
    private Long id;
    private String uuid;
    private BusinessResponse negocio;
    private ServiceResponse servicio;
    private UserResponse cliente;
    private UserResponse empleado;
    private LocalDate fechaProgramada;
    private LocalTime horaInicioProgramada;
    private LocalTime horaFinProgramada;
    private LocalDateTime creadoEn;
    private String estado;
}

