package com.example.sistemas.stailenceback.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class CreateAppointmentRequest {
    @NotNull
    private Long negocioId;

    @NotNull
    private Long servicioId;

    private Long empleadoUsuarioId;

    @NotNull
    private Long clienteUsuarioId;

    @NotNull
    private LocalDate fechaProgramada;

    @NotNull
    private LocalTime horaInicioProgramada;
}

