package com.example.sistemas.stailenceback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceRequest {
    @NotBlank
    private String nombre;

    private String descripcion;

    private String categoria;

    @NotNull
    private Integer duracionMinutos;

    @NotNull
    private BigDecimal precio;
}

