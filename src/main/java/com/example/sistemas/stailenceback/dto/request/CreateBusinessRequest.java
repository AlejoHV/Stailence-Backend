package com.example.sistemas.stailenceback.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBusinessRequest {
    @NotBlank
    private String nombre;

    private String nombreLegal;

    // slug opcional: si no se provee, el servicio lo genera
    private String slug;

    @Email
    @NotBlank
    private String correo;

    private String telefono;

    private String direccion;
}
