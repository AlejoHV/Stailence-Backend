package com.example.sistemas.stailenceback.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @Email
    private String correo;

    @NotBlank
    @Size(min = 6)
    private String password;

}
