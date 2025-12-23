package com.example.sistemas.stailenceback.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponse {
    private Long id;
    private String uuid;
    private Long negocioId;
    private Long servicioId;
    private Long clienteUsuarioId;
    private Long empleadoUsuarioId;
    private LocalDate fechaProgramada;
    private LocalTime horaInicioProgramada;
    private LocalTime horaFinProgramada;
    private String estado;
}

